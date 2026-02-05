package model.ADT;

import exceptions.ADTException;
import exceptions.MyException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDict<K, V> implements MyIDict<K, V> {
  private Map<K, V> map;
  private K key;
  private V value;

  public MyDict() {
    map = new HashMap<K, V>();
  }

  public MyDict(K key, V value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public Map<K, V> getContent() {
    return map;
  }

  @Override
  public void update(K key, V value) {
    map.put(key, value);
  }

  @Override
  public boolean isDefined(K key) {
    return map.containsKey(key);
  }

  @Override
  public V find(K key) throws MyException {
      if (! isDefined(key)) {
          throw new MyException("Key " + key + " is not defined");
      }
    return map.get(key);
  }

  @Override
  public void remove(K key) throws ADTException {
    if (!isDefined(key)) throw new ADTException("Cannot remove key: " + key + "not found.");
    map.remove(key);
  }

  @Override
  public Set<K> getKeys() {
    return map.keySet();
  }

  public String toString() {
    return map.toString();
  }
}
