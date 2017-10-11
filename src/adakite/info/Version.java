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

package adakite.info;

/**
 * Class for handling versioning info.
 */
public final class Version {

  /**
   * Release status.
   */
  public enum Status {

    ALPHA("alpha", "a"),
    BETA("beta", "b"),
    RELEASE_CANDIDATE("release candidate", "rc"),
    RELEASE("release", "")
    ;

    private final String description;
    private final String shortDescription;

    private Status(String description, String shortDescription) {
      this.description = description;
      this.shortDescription = shortDescription;
    }

    /**
     * Description of the status spelled out in lowercase.
     */
    public String getDescription() {
      return this.description;
    }

    /**
     * Common short notation for the status.
     */
    public String getShortDescription() {
      return this.shortDescription;
    }

  }

  private final Integer major;
  private final Integer minor;
  private final Integer build;
  private final Status status;

  public Version(Integer major, Integer minor, Integer build, Status status) {
    this.major = major;
    this.minor = minor;
    this.build = build;
    this.status = status;
  }

  public int getMajor() {
    return this.major;
  }

  public int getMinor() {
    return this.minor;
  }

  public int getBuild() {
    return this.build;
  }

  /**
   * @see Status
   */
  public Status getStatus() {
    return this.status;
  }

  /**
   * Returns the version as a string formatted as major.minor[.build][status]
   */
  @Override
  public String toString() {
    return ("" + this.major + "." + this.minor + "." + (this.build != null ? String.valueOf(this.build) : "") + this.status.getShortDescription());
  }

}
