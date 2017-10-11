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
