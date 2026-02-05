package model.ADT;

import exceptions.ADTException;
import exceptions.MyException;
import java.util.Map;
import java.util.Set;

public interface MyIDict<K, V> {
  void update(K key, V value);

  V find(K key) throws MyException;

  boolean isDefined(K key);

  Map<K, V> getContent();

  void remove(K key) throws ADTException;

  Set<K> getKeys();
}
