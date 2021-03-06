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

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing an entire plain text file in memory as an ArrayList of
 * String objects for each line.
 */
public final class MemoryFile  {

  private Path path;
  private List<String> lines;

  public MemoryFile() {
    this.path = null;
    this.lines = new ArrayList<>();
  }

  /**
   * Returns the path to the physical file which would be set by
   * {@link #read(java.nio.file.Path)}.
   */
  public Path getPath() {
    return this.path;
  }

  /**
   * Returns full access to the currently stored lines.
   */
  public List<String> getLines() {
    return this.lines;
  }

  /**
   * Clears the current memory file and reads the specified file into memory.
   *
   * @param path specified file to read/create
   * @see #clear()
   * @throws IOException if an I/O error occurs
   */
  public void read(Path path) throws IOException {
    clear();
    this.path = path;
    this.lines = Files.readAllLines(this.path, StandardCharsets.UTF_8);
  }

  /**
   * Dumps the currently stored lines to the specified file.
   *
   * @param path the specified file to dump lines
   * @throws FileNotFoundException
   * @throws IOException if an I/O error occurs
   */
  public void dumpToFile(Path path) throws FileNotFoundException, IOException {
    try (
        FileOutputStream fos = new FileOutputStream(path.toString());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8))
    ) {
      for (String line : this.lines) {
        bw.write(line + AdakiteUtils.newline());
      }
    }
  }

  private void clear() {
    this.path = null;
    this.lines.clear();
  }

}
