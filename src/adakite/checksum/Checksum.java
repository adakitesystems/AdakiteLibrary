package adakite.checksum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import javax.xml.bind.DatatypeConverter;

/**
 * Class for computing checksums.
 */
public final class Checksum {

  public enum Algorithm {

    MD5("MD5"),
    SHA1("SHA-1"),
    SHA256("SHA-256")
    ;

    private final String str;

    private Algorithm(String str) {
      this.str = str;
    }

    @Override
    public String toString() {
      return this.str;
    }

  }

  private final Path file;
  private final Algorithm algorithm;

  public Checksum(Path file, Algorithm algorithm) {
    this.file = file;
    this.algorithm = algorithm;
  }

  /**
   * Returns the shecksum of the specified file.
   *
   * @throws IOException if an I/O error occurs
   * @throws NoSuchAlgorithmException if the cryptographic algorithm is not available
   */
  public String get() throws IOException, NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance(this.algorithm.toString());
    md.update(Files.readAllBytes(this.file));
    byte[] digest = md.digest();
    String checksum = DatatypeConverter.printHexBinary(digest).toLowerCase(Locale.US);
    return checksum;
  }

}
