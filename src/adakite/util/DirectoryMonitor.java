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

package adakite.util;

import adakite.exception.DirectoryNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Class for monitoring a directory structure. This class can be used to
 * observe the changes in a directory from time A to time B using the
 * {@link #reset()} and {@link #update()} methods and retrieving the changes
 * via {@link #getNewFiles()}.
 */
public final class DirectoryMonitor {

  private Path path;
  private List<Path> prevFiles;
  private List<Path> currFiles;
  private List<Path> newFiles;
  private List<String> ignoreList;

  private DirectoryMonitor() {}

  public DirectoryMonitor(Path directory) {
    this.path = directory;
    this.prevFiles = new ArrayList<>();
    this.currFiles = new ArrayList<>();
    this.newFiles = new ArrayList<>();
    this.ignoreList = new ArrayList<>();
  }

  /**
   * Returns the path to the currently monitored directory.
   */
  public Path getPath() {
    return this.path;
  }

  /**
   * Returns the contents of the directory observed at the last {@link #reset()}.
   */
  public List<Path> getPreviousFiles() {
    return new ArrayList<>(this.prevFiles);
  }

  /**
   * Returns the contents of the directory observed at the last
   * {@link #reset()} or {@link #update()}.
   */
  public List<Path> getCurrentFiles() {
    return new ArrayList<>(this.currFiles);
  }

  /**
   * Returns the contents of the directory observed at the last
   * {@link #reset()} or {@link #update()}.
   */
  public List<Path> getNewFiles() {
    return new ArrayList<>(this.newFiles);
  }

  /**
   * Returns the ignore list which contains the names of files or directories
   * that should be ignored.
   */
  public List<String> getIgnoreList() {
    return this.ignoreList;
  }

  /**
   * Resets the new files list and refreshes both previous and current files
   * lists.
   *
   * @throws IOException if an I/O error occurs
   * @throws DirectoryNotFoundException
   */
  public void reset() throws IOException, DirectoryNotFoundException {
    this.newFiles.clear();
    this.prevFiles.clear();

    boolean found;
    Path[] contents = AdakiteUtils.getDirectoryContents(this.path);
    for (Path path : contents) {
      String pathLower = path.toAbsolutePath().toString().toLowerCase(Locale.US);
      found = false;
      for (String str : this.ignoreList) {
        if (pathLower.contains(str.toLowerCase(Locale.US))) {
          found = true;
          break;
        }
      }
      if (!found) {
        this.prevFiles.add(path);
      }
    }

    this.currFiles = new ArrayList<>(this.prevFiles);
  }

  /**
   * Refreshes the current files list and fills the new files list by comparing
   * the previous files list to the current files list.
   *
   * @throws DirectoryNotFoundException
   * @throws IOException if an I/O error occurs
   * @see #getNewFiles()
   */
  public void update() throws IOException, DirectoryNotFoundException {
    this.newFiles.clear();
    this.currFiles.clear();

    boolean found;
    Path[] contents = AdakiteUtils.getDirectoryContents(this.path);
    for (Path path : contents) {
      String pathLower = path.toAbsolutePath().toString().toLowerCase(Locale.US);
      found = false;
      for (String str : this.ignoreList) {
        if (pathLower.contains(str.toLowerCase(Locale.US))) {
          found = true;
          break;
        }
      }
      if (!found) {
        this.currFiles.add(path);
      }
    }

    for (Path path : this.currFiles) {
      if (!this.prevFiles.contains(path)) {
        this.newFiles.add(path);
      }
    }
  }

}
