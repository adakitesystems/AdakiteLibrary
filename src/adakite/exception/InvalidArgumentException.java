package adakite.exception;

/**
 * Checked version of {@link java.lang.IllegalArgumentException}.
 */
public final class InvalidArgumentException extends Exception {

  public InvalidArgumentException() {
    super();
  }

  public InvalidArgumentException(String message) {
    super(message);
  }

}
