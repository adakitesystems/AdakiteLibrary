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
