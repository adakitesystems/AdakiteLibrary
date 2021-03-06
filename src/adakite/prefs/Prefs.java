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

package adakite.prefs;

import adakite.util.AdakiteUtils;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Wrapper class for {@link java.util.prefs.Preferences}.
 */
public final class Prefs {

  private Preferences prefs;

  private Prefs() {}

  public Prefs(Preferences prefs) {
    this.prefs = prefs;
  }

  /**
   * Sets the specified key to the specified value.
   *
   * @param key specified key
   * @param val specified value
   */
  public void set(String key, String val) {
    this.prefs.put(key, val);
  }

  /**
   * Returns the corresponding value of the specified key.
   *
   * @param key specified key
   * @throws IllegalStateException if the value has not been previously set
   */
  public String get(String key) {
    String val = this.prefs.get(key, "");
    if (AdakiteUtils.isNullOrEmpty(val)) {
      throw new IllegalStateException("value not set for: " + key);
    }
    return val;
  }

  /**
   * Removes this preference node and all of its descendants, invalidating
   * any preferences contained in the removed nodes.
   *
   * @see java.util.prefs.Preferences#removeNode()
   * @throws BackingStoreException if this operation cannot be completed
   *     due to a failure in the backing store, or inability to
   *     communicate with it.
   */
  public void clear() throws BackingStoreException {
    try {
      this.prefs.removeNode();
    } catch (BackingStoreException ex) {
      throw ex;
    } catch (Exception ex) {
      /* Do nothing. */
    }
  }

  /**
   * Tests whether the specified key has been previously set.
   *
   * @param key specified key
   */
  public boolean hasValue(String key) {
    try {
      get(key);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  /**
   * Tests whether the corresponding value to the specified key is TRUE
   * or FALSE.
   *
   * @param key specified key
   */
  public boolean isEnabled(String key) {
    try {
      String val = get(key);
      return val.equalsIgnoreCase(Boolean.TRUE.toString());
    } catch (Exception ex) {
      return false;
    }
  }

  /**
   * Sets the specified key to the specified boolean value.
   *
   * @param key specified key
   * @param enabled specified boolean value
   */
  public void setEnabled(String key, boolean enabled) {
    set(key, Boolean.toString(enabled));
  }

  /**
   * Creates and/or returns the specified child node.
   *
   * @param nodeName specified child node
   */
  public Prefs getChild(String nodeName) {
    Preferences child = this.prefs.node(nodeName);
    return new Prefs(child);
  }

}
