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

package adakite.windows.registry;

import adakite.util.AdakiteUtils;
import adakite.process.CommandBuilder;
import adakite.process.SimpleProcess;
import adakite.util.AdakiteUtils.StringCompareOption;
import adakite.windows.Windows;
import adakite.windows.registry.exception.RegistryQueryException;
import adakite.windows.registry.exception.RegistryEntryNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Class for handling Windows Registry operations.
 */
public final class WinRegistry {

  public enum Subtree {
    HKEY_CLASSES_ROOT,
    HKEY_CURRENT_USER,
    HKEY_LOCAL_MACHINE,
    HKEY_USERS,
    HKEY_CURRENT_CONFIG
  }

  /**
   * Resulting error message displayed when a "reg query" command fails.
   */
  private static final String QUERY_ERROR_MESSAGE = "ERROR: The system was unable to find the specified registry key or value.";

  private WinRegistry() {}

  /**
   * Returns the REG_SZ value associated with the specified "entryName".
   *
   * @param path specified path to the "entryName"
   * @param entryName name of the specified entry
   * @throws IOException if an I/O error occurs
   * @throws RegistryEntryNotFoundException if an error occurs or the specified
   *     path and entry name do not contain a REG_SZ value
   * @throws RegistryQueryException
   */
  public static String strValue(String path, String entryName) throws IOException,
                                                                      RegistryEntryNotFoundException,
                                                                      RegistryQueryException {
    /* Get list of possible entries. */
    List<String> queryResult = query(path);

    /* Find the result containing the specified "entryName". */
    String ret = null;
    for (String result : queryResult) {
      if (AdakiteUtils.isNullOrEmpty(result, StringCompareOption.TRIM)) {
        continue;
      }

      /**
       * The query result prefixes each entry with four space characters.
       * Remove them.
       */
      result = result.substring(4, result.length());

      /* Find the entry type. */
      RegEntry.Type type = getType(result);
      if (type == null || type != RegEntry.Type.REG_SZ) {
        continue;
      }
      int typeIndex = result.indexOf(type.toString());

      String tmpName = result.substring(0, typeIndex - 4);
      if (!tmpName.equalsIgnoreCase(entryName)) {
        /* Wrong entry. */
        continue;
      }

      ret = result.substring(typeIndex + type.toString().length() + 4, result.length());
    }
    if (ret != null) {
      return ret;
    }

    /* Something went wrong. */
    throw new RegistryEntryNotFoundException();
  }

  private static List<String> query(String path) throws IOException,
                                                        RegistryQueryException {
    CommandBuilder command = new CommandBuilder();
    command.setFile(Windows.Program.REG.getPath());
    command.addArg("query");
    command.addArg(path);

    SimpleProcess process = new SimpleProcess(command.getFile(), command.getArgs());
    Thread thread = new Thread(process);
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException ex) {
      /* Do nothing. */
    }

    List<String> log = process.getStdoutLog();
    if (log == null
        || log.isEmpty()
        || log.get(0).startsWith(QUERY_ERROR_MESSAGE)) {
      throw new RegistryQueryException();
    }

    return process.getStdoutLog();
  }

  private static RegEntry.Type getType(String queryLine) {
    String[] tokens = queryLine.split(" ");
    for (String token : tokens) {
      for (RegEntry.Type val : RegEntry.Type.values()) {
        if (token.equalsIgnoreCase(val.toString())) {
          return val;
        }
      }
    }
    return null;
  }

}
