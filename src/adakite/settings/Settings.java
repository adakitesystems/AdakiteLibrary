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

package adakite.settings;

import adakite.util.AdakiteUtils;
import adakite.util.AdakiteUtils.StringCompareOption;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for storing settings pairs in memory.
 */
public final class Settings {

  private final ConcurrentHashMap<String, String> settings;

  public Settings() {
    this.settings = new ConcurrentHashMap<>();
  }

  public Settings(Settings settings) {
    this.settings = new ConcurrentHashMap<>();
    Enumeration<String> enums = settings.getKeys();
    while (enums.hasMoreElements()) {
      String key = enums.nextElement();
      set(key, settings.getValue(key));
    }
  }

  /**
   * Tests if the specified key is present.
   *
   * @param key the specified key
   */
  public boolean containsKey(String key) {
    return this.settings.containsKey(key);
  }

  /**
   * Returns the set of keys.
   */
  public Enumeration<String> getKeys() {
    return this.settings.keys();
  }

  /**
   * Sets the specified key with the specified value.
   *
   * @param key specified key
   * @param value specified value
   */
  public void set(String key, String value) {
    this.settings.put(key, value);
  }

  /**
   * Removes the key (and its corresponding value) from this map.
   * This method does nothing if the key is not in the map.
   *
   * @param key the key that needs to be removed
   * @return
   *     the previous value associated with key,
   *     otherwise null if there was no mapping for key
   */
  public String remove(String key) {
    return this.settings.remove(key);
  }

  /**
   * Returns the value associated with the specified key.
   *
   * @param key specified key
   */
  public String getValue(String key) {
    return this.settings.get(key);
  }

  /**
   * Tests whether the specified key has an associated value.
   *
   * @param key specified key
   */
  public boolean hasValue(String key) {
    return (!AdakiteUtils.isNullOrEmpty(getValue(key), StringCompareOption.TRIM));
  }

}
