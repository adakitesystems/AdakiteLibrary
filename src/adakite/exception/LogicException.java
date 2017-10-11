package adakite.exception;

/**
 * Unchecked exception for indicating a logic error of which the
 * developer is at fault.
 */
public final class LogicException extends RuntimeException {

  public LogicException() {
    super();
  }

  public LogicException(String message) {
    super(message);
  }

}
