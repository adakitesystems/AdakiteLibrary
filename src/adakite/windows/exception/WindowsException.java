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

package adakite.windows.exception;

public class WindowsException extends Exception {

  public static enum SystemError {
    ERROR_MOD_NOT_FOUND(126, "The specified module could not be found. System Error 126 (0x7E)"),
    ERROR_ELEVATION_REQUIRED(740, "The requested operation requires elevation. System Error 740 (0x2E4)")
    ;

    private final int val;
    private final String description;

    private SystemError(int val, String description) {
      this.val = val;
      this.description = description;
    }

    public int intVal() {
      return this.val;
    }

    @Override
    public String toString() {
      return this.description;
    }
  }

  public WindowsException() {
    super();
  }

  public WindowsException(String message) {
    super(message);
  }

}
