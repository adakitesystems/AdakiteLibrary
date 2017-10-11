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

package adakite.util;

import adakite.exception.DirectoryNotFoundException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utitilies class for useful or wrapped variables and methods.
 */
public final class AdakiteUtils {

  public enum StringCompareOption {

    TRIM
    ;

  }

  public enum DirectoryTraverseOption {

    OMIT_DIRECTORY_NAMES
    ;

  }

  /**
   * Wrapped Java's built-in platform-dependent newline character.
   */
  private static final String NEWLINE = System.lineSeparator();

  private AdakiteUtils() {}

  /**
   * Tests whether the specified string is null or empty.
   *
   * @param str the specified string
   * @param options
   * @return
   *     true if string is null or the length is less than one,
   *     otherwise false
   */
  public static boolean isNullOrEmpty(String str, StringCompareOption... options) {
    if (str == null) {
      return true;
    }
    if (options != null && options.length > 0) {
      for (StringCompareOption option : options) {
        if (option == StringCompareOption.TRIM) {
          str = str.trim();
          break;
        }
      }
    }
    return (str.length() < 1);
  }

  /**
   * Tests whether the specified string is null or empty.
   *
   * @param str the specified string
   * @return
   *     true if string is null or the length is less than one,
   *     otherwise false
   */
  public static boolean isNullOrEmpty(String str) {
    return (str == null || str.length() < 1);
  }

  /**
   * Tests whether the specfied two strings are both non-null and equal
   * to each other.
   *
   * @param str1 specified first string
   * @param str2 specified second string
   */
  public static boolean isNonNullAndEqual(String str1, String str2) {
    return (str1 != null && str2 != null && str1.equals(str2));
  }

  /**
   * Returns n number of system-dependent newline characters.
   *
   * @param n number of newline characters to return
   */
  public static String newline(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("invalid number of newlines: " + n);
    }
    StringBuilder sb = new StringBuilder(n);
    for (int i = 0; i < n; ++i) {
      sb.append(NEWLINE);
    }
    return sb.toString();
  }

  /**
   * Returns one system-dependent newline character.
   *
   * @see #newline(int)
   */
  public static String newline() {
    return NEWLINE;
  }

  /**
   * Returns a string whose value is this string, with any leading
   * whitespace removed.
   *
   * @param str specified string
   */
  public static String trimLeft(String str) {
    int index;
    for (index = 0; index < str.length(); ++index) {
      char ch = str.charAt(index);
      if (ch != ' ' && ch != '\t') {
        break;
      }
    }
    return str.substring(index, str.length());
  }

  /**
   * Returns a string whose value is this string, with any trailing
   * whitespace removed.
   *
   * @param str specified string
   */
  public static String trimRight(String str) {
    int index;
    for (index = str.length() - 1; index >= 0; --index) {
      char ch = str.charAt(index);
      if (ch != ' ' && ch != '\t') {
        break;
      }
    }
    return str.substring(0, index + 1);
  }

  /**
   * Returns a string whose value is this string, with any leading, trailing
   * and excess infix whitespace removed. "Excess infix" whitespace is described
   * as more than one whitespace between words. In order words, each word will
   * have exactly one whitespace between them.
   *
   * @param str specified string
   */
  public static String trimAll(String str) {
    if (str.isEmpty()) {
      return "";
    }

    String[] tokens = str
        .replace("\t", " ")
        .trim()
        .split(" ");
    StringBuilder ret = new StringBuilder(tokens.length);
    ret.append(tokens[0]);
    for (int i = 1; i < tokens.length; i++) {
      ret.append(" ").append(tokens[i]);
    }

    return ret.toString();
  }

  /**
   * Tests if the specified file is readable.
   *
   * @param file specified path to file
   */
  public static boolean fileReadable(Path file) {
    return Files.isReadable(file);
  }

  /**
   * Tests if the specified file is writable.
   *
   * @param file specified path to file
   */
  public static boolean fileWritable(Path file) {
    return Files.isWritable(file);
  }

  /**
   * Tests if the specified file is not null and exists.
   *
   * @param file the specified path to file
   */
  public static boolean fileExists(Path file) {
    return (file != null && Files.isRegularFile(file));
  }

  /**
   * Tests if the specified directory is not null and exists.
   *
   * @param directory the specified path to directory
   */
  public static boolean directoryExists(Path directory) {
    return (directory != null && Files.isDirectory(directory));
  }

  /**
   * Returns the parent directory of the specified path.
   *
   * @param path the specified path
   * @return
   *     the parent directory of the specified path if parent exists,
   *     otherwise null
   */
  public static Path getParentDirectory(Path path) {
    return path.getParent();
  }

  /**
   * Returns a list of all files and subdirectories recursively. Only
   * supports regular files and directories.
   *
   * @param directory the specified path to directory
   * @param options
   * @return
   *     a list of all files and subdirectories,
   *     otherwise an empty list if directory does not exist or is empty
   * @throws IOException
   * @throws DirectoryNotFoundException
   */
  public static Path[] getDirectoryContents(Path directory, DirectoryTraverseOption... options) throws IOException, DirectoryNotFoundException {
    if (!directoryExists(directory)) {
      throw new DirectoryNotFoundException();
    }

    List<DirectoryTraverseOption> directoryTraverseOptions = new ArrayList<>();
    if (options != null && options.length == 1) {
      for (DirectoryTraverseOption option : options) {
        directoryTraverseOptions.add(option);
      }
    }

    List<Path> list = new ArrayList<>();
    Files.walk(directory)
        .forEach(p -> {
          if (fileExists(p) || (directoryExists(p) && !directoryTraverseOptions.contains(DirectoryTraverseOption.OMIT_DIRECTORY_NAMES))) {
            list.add(p);
          }
        });

    Path[] ret = new Path[list.size()];
    for (int i = 0; i < list.size(); i++) {
      ret[i] = Paths.get(list.get(i).toString());
    }

    return ret;
  }

  /**
   * Creates the specified directory.
   *
   * @param directory the specified path of the directory
   * @throws IOException
   */
  public static void createDirectory(Path directory) throws IOException {
    if (!directoryExists(directory)) {
      Files.createDirectories(directory);
    }
  }

  /**
   * Creates the parent directory of the specified path. This method
   * does not throw an exception if the parent directory is null.
   *
   * @param directory the specified path
   * @throws IOException if an I/O error occurs
   */
  public static void createParentDirectory(Path directory) throws IOException {
    Path parent = getParentDirectory(directory);
    if (parent != null) {
      createDirectory(parent);
    }
  }

  /**
   * Creates the specified file.
   *
   * @param file the specified path to the file
   * @throws IOException if an I/O error occurs
   */
  public static void createFile(Path file) throws IOException {
    if (!fileExists(file)) {
      createParentDirectory(file);
      Files.createFile(file);
    }
  }

  /**
   * Deletes the specified file.
   *
   * @param file the specified path to the file
   * @throws IOException if an I/O error occurs
   */
  public static void deleteFile(Path file) throws IOException {
    if (fileExists(file)) {
      Files.delete(file);
    }
  }

  /**
   * Appends the specified string to the specified file.
   *
   * @param file specified path to the file
   * @param str specified string
   * @throws IOException if an I/O error occurs
   */
  public static void appendToFile(Path file, String str) throws IOException {
    if (!fileExists(file)) {
      createFile(file);
    }
    try (
        FileOutputStream fos = new FileOutputStream(file.toString(), true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8))
    ) {
      bw.write(str);
    }
  }

//  /**
//   * Returns the file extension at the end of the specified file path. The
//   * path may also be a directory in which case it will return the string
//   * following the last period character.
//   *
//   * @param file specified file
//   * @return
//   *     the file extension at the end of the specified file path,
//   *     otherwise null if path string does not contain a file extension
//   */
//  public static String getFileExtension(Path file) {
//    String pathStr = file.getFileName().toString();
//    int index = pathStr.lastIndexOf('.');
//    return (index < 0) ? null : pathStr.substring(index + 1, pathStr.length());
//  }

  /**
   * Returns a padded string with the specified pad character if the specified
   * string's length is less than the specified total length.
   *
   * @param str specified string
   * @param totalLength specified total length for padding computation
   * @param padCharacter
   */
  public static String pad(String str, int totalLength, char padCharacter) {
    StringBuilder sb = new StringBuilder(str);
    int n = str.length() - totalLength;
    for (int i = 0; i < n; i++) {
      sb.append(padCharacter);
    }
    return sb.toString();
  }

  /**
   * Returns a padded string with spaces if the specified
   * string's length is less than the specified total length.
   *
   * @param str specified string
   * @param totalLength specified total length for padding computation
   */
  public static String pad(String str, int totalLength) {
    return pad(str, totalLength, ' ');
  }

  /**
   * Tests if the specified array contains the specified key.
   *
   * @param arr the specified array
   * @param key the specified key
   */
  public static boolean contains(byte[] arr, byte[] key) {
    int ilen = arr.length - key.length + 1;
    int jlen = key.length;
    for (int i = 0; i < ilen; ++i) {
      int j = 0;
      for (j = 0; j < jlen; ++j) {
        if (arr[i + j] != key[j]) {
          break;
        }
      }
      if (j >= jlen) {
        return true;
      }
    }
    return false;
  }

}
