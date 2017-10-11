package adakite.process;

import adakite.debugging.Debugging;
import adakite.util.AdakiteUtils;
import adakite.util.AdakiteUtils.StringCompareOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for building objects to pass to process objects
 * (e.g. passing arguments to a ProcessBuilder object).
 */
public final class CommandBuilder {

  private Path file;
  private List<String> args;

  public CommandBuilder() {
    this.file = null;
    this.args = new ArrayList<>();
  }

  /**
   * Returns the path to the executable.
   */
  public Path getFile() {
    return this.file;
  }

  /**
   * Sets the path to the specified executable.
   *
   * @param file path to the specified executable
   */
  public CommandBuilder setFile(Path file) {
    if (file == null) {
      throw new IllegalArgumentException(Debugging.Message.NULL_OBJECT.toString("path"));
    } else {
      this.file = file;
      return this;
    }
  }

  /**
   * Returns a String array of the command and arguments.
   */
  public String[] get() {
    if (this.file == null) {
      throw new IllegalStateException(Debugging.Message.NULL_OBJECT.toString("path"));
    } else {
      String[] command = new String[1 + this.args.size()];
      command[0] = this.file.toString();
      for (int i = 0; i < this.args.size(); i++) {
        command[i + 1] = this.args.get(i);
      }
      return command;
    }
  }

  /**
   * Adds the specified arguments to the list of arguments.
   *
   * @param args specified arguments
   */
  public CommandBuilder addArg(String... args) {
    if (args == null || args.length < 1) {
      throw new IllegalArgumentException(Debugging.Message.NULL_OBJECT.toString("args"));
    } else {
      for (int i = 0; i < args.length; ++i) {
        String arg = args[i];
        if (AdakiteUtils.isNullOrEmpty(arg, StringCompareOption.TRIM)) {
          throw new IllegalArgumentException(Debugging.Message.NULL_OBJECT.toString("empty arg at index: " + i));
        }
        this.args.add(arg);
      }
      return this;
    }
  }

  /**
   * Returns the argument list as a String array.
   */
  public String[] getArgs() {
    if (this.args.size() < 1) {
      return new String[]{};
    } else {
      String[] args = new String[this.args.size()];
      for (int i = 0; i < this.args.size(); i++) {
        args[i] = this.args.get(i);
      }
      return args;
    }
  }

  /**
   * Replaces the current argument list with the specified argument list.
   *
   * @param args specified argument list.
   */
  public CommandBuilder setArgs(String[] args) {
    this.args.clear();
    this.args.addAll(Arrays.asList(args));
    return this;
  }

  /**
   * Returns the command and arguments as one string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (this.file == null) {
      sb.append("null");
    } else {
      sb.append(this.file.toString());
    }
    for (String arg : this.args) {
      sb.append(" ").append(arg);
    }
    return sb.toString();
  }

}
