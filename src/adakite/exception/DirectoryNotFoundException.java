package adakite.exception;

/**
 * Directory version of {@link java.io.FileNotFoundException}.
 */
public final class DirectoryNotFoundException extends Exception {

  public DirectoryNotFoundException() {
    super();
  }

  public DirectoryNotFoundException(String message) {
    super(message);
  }

}
