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

package adakite.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for running a program and logging its output. This class does not
 * handle errors such that if the specified program does not successfully
 * terminate itself.
 */
public class SimpleProcess implements Runnable {

  private final Path file;
  private final String[] args;
  private List<String> stdoutLog;
  private List<String> stderrLog;

  public SimpleProcess(Path path, String[] args) {
    this.file = path;
    this.args = args;
    this.stdoutLog = new ArrayList<>();
    this.stderrLog = new ArrayList<>();
  }

  public List<String> getStdoutLog() {
    return new ArrayList<>(this.stdoutLog);
  }

  public List<String> getStderrLog() {
    return new ArrayList<>(this.stderrLog);
  }

  @Override
  public void run() {
    this.stdoutLog.clear();

    CommandBuilder command = new CommandBuilder();
    command.setFile(this.file);
    if (this.args != null) {
      command.setArgs(this.args);
    }

    Process process;
    try {
      process = new ProcessBuilder(command.get()).start();

      try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
        String line;
        while ((line = br.readLine()) != null) {
          this.stdoutLog.add(line);
        }
      } catch (IOException ex) {
        //TODO: Handle error?
      }

      try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {
        String line;
        while ((line = br.readLine()) != null) {
          this.stderrLog.add(line);
        }
      } catch (IOException ex) {
        //TODO: Handle error?
      }
    } catch (IOException ex) {
      //TODO: Handle error?
    }
  }

}
