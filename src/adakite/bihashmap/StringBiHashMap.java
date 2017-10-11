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

package adakite.bihashmap;

import adakite.bihashmap.exception.DuplicateMappingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class StringBiHashMap {

  private final HashMap<String, String> map;
  private final HashMap<String, String> inverse;

  public StringBiHashMap() {
    this.map = new HashMap<>();
    this.inverse = new HashMap<>();
  }

  /**
   * Adds the specified key and value to the hash map.
   *
   * @param key specified key
   * @param val corresponding value
   * @throws DuplicateMappingException mapping already exists
   */
  public void put(String key, String val) throws DuplicateMappingException {
    if (contains(key) || contains(val)) {
      throw new DuplicateMappingException(
          String.format("Mapping already exists:%n  key: %s%n  val: %s", key, val)
      );
    }
    this.map.put(key, val);
    this.inverse.put(val, key);
  }

  public Set<String> getKeySet() {
    return this.map.keySet();
  }

  public String get(String searchKey) {
    String result;

    result = getByKey(searchKey);
    if (result != null) {
      return result;
    }

    result = getByValue(searchKey);
    if (result != null) {
      return result;
    }

    return null;
  }

  public String getByKey(String key) {
    return this.map.get(key);
  }

  public String getByValue(String val) {
    return this.inverse.get(val);
  }

  public boolean isKey(String searchKey) {
    return (getByKey(searchKey) != null);
  }

  public boolean isValue(String searchKey) {
    return (getByValue(searchKey) != null);
  }

  public Set<Map.Entry<String, String>> entrySet() {
    return this.map.entrySet();
  }

  public void clear() {
    this.map.clear();
    this.inverse.clear();
  }

  public boolean contains(String searchKey) {
    return (this.map.containsKey(searchKey) || this.map.containsValue(searchKey));
  }

  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  public int size() {
    return this.map.size();
  }

  public void remove(String searchKey) {
    if (isKey(searchKey)) {
      this.inverse.remove(getByKey(searchKey));
      this.map.remove(searchKey);
    } else if (isValue(searchKey)) {
      this.map.remove(getByValue(searchKey));
      this.inverse.remove(searchKey);
    }
  }

}
