////////////////////////////////////////////////////////////////////////////////
//
//  Copyright 2017 Adakite
//
//  Permission is hereby granted, free of charge, to any person obtaining a
//  copy of this software and associated documentation files (the "Software"),
//  to deal in the Software without restriction, including without limitation
//  the rights to use, copy, modify, merge, publish, distribute, sublicense,
//  and/or sell copies of the Software, and to permit persons to whom the
//  Software is furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
//  DEALINGS IN THE SOFTWARE.
//
////////////////////////////////////////////////////////////////////////////////

package adakite.windows.task;

import adakite.process.CommandBuilder;
import adakite.util.AdakiteUtils;
import adakite.process.SimpleProcess;
import adakite.util.AdakiteUtils.StringCompareOption;
import adakite.windows.Windows;
import adakite.windows.task.exception.TasklistParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for getting and storing a list of processes using
 * the Windows Tasklist program.
 */
public final class Tasklist {

  private static final Logger LOGGER = Logger.getLogger(Tasklist.class.getName());

  /**
   * Enum class containing column headers for output of "tasklist.exe" in
   * combination of using
   * {@link droplauncher.util.windows.Windows#DEFAULT_TASKLIST_ARGS}.
   */
  public enum ColumnHeader {

    IMAGE_NAME("Image Name"),
    PID("PID"),
    SESSION_NAME("Session Name"),
    SESSION_NUMBER("Session Number"),
    MEM_USAGE("Mem Usage"),
    STATUS("Status"),
    USERNAME("User Name"),
    CPU_TIME("CPU Time"),
    WINDOW_TITLE("Window Title")
    ;

    private final String str;

    private ColumnHeader(String str) {
      this.str = str;
    }

    @Override
    public String toString() {
      return this.str;
    }

  }

  private final List<Task> tasks;

  public Tasklist() {
    this.tasks = new ArrayList<>();
  }

  /**
   * Kill the task indicated by the specified process ID.
   *
   * @param pid specified process ID
   * @throws IOException if an I/O error occurs
   */
  public static void kill(String pid) throws IOException {
    CommandBuilder command = new CommandBuilder()
        .setFile(Windows.Program.TASKKILL.getPath().toAbsolutePath())
        .setArgs(Windows.Program.TASKKILL.getPredefinedArgs())
        .addArg(pid);
    SimpleProcess process = new SimpleProcess(command.getFile(), command.getArgs());
    Thread thread = new Thread(process);
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException ex) {
      /* Do nothing. */
    }
  }

  /**
   * Returns a list of Task objects.
   *
   * @param update whether to update the list before returning
   *
   * @throws IOException if an I/O error occurs
   * @throws TasklistParseException
   */
  public List<Task> getTasks(boolean update) throws IOException, TasklistParseException {
    if (update) {
      update();
    }
    return this.tasks;
  }

  /**
   * Returns a list of Task objects. Does NOT update the task list
   * before returning.
   *
   * @see #getTasks(boolean)
   * @throws IOException if an I/O error occurs
   */
  public List<Task> getTasks() throws IOException, TasklistParseException {
    return getTasks(false);
  }

  /**
   * Runs the Windows Tasklist program and updates the internal tasklist.
   *
   * @throws IOException if an I/O error occurs
   * @throws TasklistParseException
   */
  public void update() throws IOException, TasklistParseException {
    this.tasks.clear();

    SimpleProcess process = new SimpleProcess(Windows.Program.TASKLIST.getPath().toAbsolutePath(), Windows.Program.TASKLIST.getPredefinedArgs());
    Thread thread = new Thread(process);
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException ex) {
      /* Do nothing. */
    }

    /* Find beginning of process list. */
    int index;
    for (index = 0; index < process.getStdoutLog().size(); index++) {
      String line = process.getStdoutLog().get(index);
      if (line.startsWith("=")) {
        break;
      }
    }
    if (index >= process.getStdoutLog().size()) {
      throw new TasklistParseException("error parsing Tasklist output");
    }

    /* Determine length of each column. */
    List<Integer> colLengths = new ArrayList<>();
    String colLine = process.getStdoutLog().get(index);
    StringTokenizer st = new StringTokenizer(colLine);
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      colLengths.add(token.length());
    }

    /* Go to first line with a task entry. */
    index++;

    /* Parse remaining lines. */
    for (int i = index; i < process.getStdoutLog().size(); i++) {
      String line = process.getStdoutLog().get(i);
      if (AdakiteUtils.isNullOrEmpty(line, StringCompareOption.TRIM)) {
        continue;
      }

      /* Tokenize line using the column lengths. */
      List<String> tokens = tokenizeTaskEntry(line, colLengths);
      if (tokens.size() < ColumnHeader.values().length) {
        throw new TasklistParseException("error parsing task entry line");
      }

      /* Set task information. */
      Task task = new Task();
      int tokenIndex = 0;
      task.setImageName(tokens.get(tokenIndex++));
      task.setPID(tokens.get(tokenIndex++));
      task.setSessionName(tokens.get(tokenIndex++));
      task.setSessionNumber(tokens.get(tokenIndex++));
      task.setMemUsage(tokens.get(tokenIndex++));
      task.setStatus(tokens.get(tokenIndex++));
      task.setUsername(tokens.get(tokenIndex++));
      task.setCpuTime(tokens.get(tokenIndex++));
      task.setWindowTitle(tokens.get(tokenIndex++));

      /* Add task to main task list. */
      this.tasks.add(task);
    }
  }

  private List<String> tokenizeTaskEntry(String str, List<Integer> colLengths) {
    List<String> ret = new ArrayList<>();

    int colIndex = 0;
    for (int i = 0; i < colLengths.size(); i++) {
      String tmp = str.substring(colIndex, colIndex + colLengths.get(i)).trim();
      ret.add(tmp);
      colIndex += colLengths.get(i) + 1;
    }

    return ret;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Tasklist)) {
      return false;
    } else if (this == obj) {
      return true;
    } else {
      try {
        Tasklist tasklist = (Tasklist) obj;
        for (Task task1 : tasklist.getTasks()) {
          boolean isFound = false;
          for (Task task2 : this.getTasks()) {
            if (task1.getPID().equals(task2.getPID())) {
              isFound = true;
              break;
            }
          }
          if (!isFound) {
            return false;
          }
        }
      } catch (Exception ex) {
        LOGGER.log(Level.SEVERE, null, ex);
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.tasks);
  }

}
