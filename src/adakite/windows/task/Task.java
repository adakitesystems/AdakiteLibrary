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

import java.util.Objects;

/**
 * Container class for process information obtained from Windows Tasklist.
 */
public final class Task {

  private String imageName;
  private String pid;
  private String sessionName;
  private String sessionNumber;
  private String memUsage;
  private String status;
  private String username;
  private String cpuTime;
  private String windowTitle;

  public Task() {
    this.imageName = "";
    this.pid = "";
    this.sessionName = "";
    this.sessionNumber = "";
    this.memUsage = "";
    this.status = "";
    this.username = "";
    this.cpuTime = "";
    this.windowTitle = "";
  }

  public Task(Task task) {
    this.imageName = task.getImageName();
    this.pid = task.getPID();
    this.sessionName = task.getSessionName();
    this.sessionNumber = task.getSessionNumber();
    this.memUsage = task.getMemUsage();
    this.status = task.getStatus();
    this.username = task.getUsername();
    this.cpuTime = task.getCpuTime();
    this.windowTitle = task.getWindowTitle();
  }

  public String getImageName() {
    return this.imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public String getPID() {
    return this.pid;
  }

  public void setPID(String pid) {
    this.pid = pid;
  }

  public String getSessionName() {
    return this.sessionName;
  }

  public void setSessionName(String sessionName) {
    this.sessionName = sessionName;
  }

  public String getSessionNumber() {
    return this.sessionNumber;
  }

  public void setSessionNumber(String sessionNumber) {
    this.sessionNumber = sessionNumber;
  }

  public String getMemUsage() {
    return this.memUsage;
  }

  public void setMemUsage(String memUsage) {
    this.memUsage = memUsage;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getCpuTime() {
    return this.cpuTime;
  }

  public void setCpuTime(String cpuTime) {
    this.cpuTime = cpuTime;
  }

  public String getWindowTitle() {
    return this.windowTitle;
  }

  public void setWindowTitle(String windowTitle) {
    this.windowTitle = windowTitle;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Task)) {
      return false;
    } else if (this == obj) {
      return true;
    } else {
      Task task = (Task) obj;
      return getPID().equals(task.getPID());
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPID());
  }

}
