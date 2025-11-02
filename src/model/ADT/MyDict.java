package model.ADT;

import java.util.HashMap;
import java.util.Map;

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
  public V find(K key) {
    return map.get(key);
  }
}
