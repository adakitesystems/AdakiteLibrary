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

package adakite.debugging;

import java.util.logging.Logger;

/**
 * Debugging utilities class for anything related to debugging and logging.
 */
public final class Debugging {

  public enum Message {

    NULL_OR_EMPTY_STRING("null or empty string"),
    ACK("ack"),
    NULL_OBJECT("null object"),
    FILE_INACCESSIBLE_OR_DOES_NOT_EXIST("file inaccessible or does not exist"),
    FILE_ALREADY_EXISTS("file already exists"),
    OPEN_FAIL("open failed"),
    CREATE_FAIL("create failed"),
    DELETE_FAIL("delete failed"),
    OPERATION_FAIL("operation failed"),
    APPEND_FAIL("append failed"),
    CANNOT_BE_NULL("cannot be null"),
    CANNOT_BE_NULL_OR_EMPTY("cannot be null or empty"),
    VALUE_NOT_SET_FOR_KEY("value not set for key")
    ;

    private final String str;

    private Message(String str) {
      this.str = str;
    }

    @Override
    public String toString() {
      return this.str;
    }

    public String toString(String message) {
      return (this.str + ": " + message);
    }

  }

  private static final Logger LOGGER = Logger.getLogger(Debugging.class.getName());

  private Debugging() {}

}
