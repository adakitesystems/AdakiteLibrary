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

import adakite.windows.task.exception.TasklistParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for tracking newly created tasks in the Windows Tasklist.
 */
public final class TaskTracker {

  private final Tasklist previousTasklist;
  private final Tasklist currentTasklist;
  private final List<Task> newTasks;

  public TaskTracker() {
    this.previousTasklist = new Tasklist();
    this.currentTasklist = new Tasklist();
    this.newTasks = new ArrayList<>();
  }

  public Tasklist getCurrentTasklist() {
    return this.currentTasklist;
  }

  public Tasklist getPreviousTasklist() {
    return this.previousTasklist;
  }

  public List<Task> getNewTasks() {
    return this.newTasks;
  }

  /**
   * Resets both the current and previous tasklists.
   *
   * @throws IOException if an I/O error occurs
   * @throws TasklistParseException
   */
  public void reset() throws IOException, TasklistParseException {
    this.newTasks.clear();
    this.previousTasklist.update();
    this.currentTasklist.update();
  }

  /**
   * Updates the current tasklist and compares it against the previous
   * tasklist to determine which currently running tasks are new.
   *
   * @throws IOException if an I/O error occurs
   * @throws TasklistParseException
   * @see #getNewTasks()
   */
  public void update() throws IOException, TasklistParseException {
    this.newTasks.clear();
    this.currentTasklist.update();
    for (Task currTask : this.currentTasklist.getTasks()) {
      if (!this.previousTasklist.getTasks().contains(currTask)) {
        this.newTasks.add(currTask);
      }
    }
  }

}
