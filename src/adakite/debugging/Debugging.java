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
