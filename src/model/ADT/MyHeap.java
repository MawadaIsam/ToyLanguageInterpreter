package model.ADT;

import exceptions.MyException;
import java.util.HashMap;
import java.util.Map;

public class MyHeap<K, V> implements MyIHeap<K, V> {

  private Map<K, V> heap;
  private int nextFreeAddress;

  public MyHeap() {
    this.heap = new HashMap<K, V>();
    this.nextFreeAddress = 1;
  }

  @Override
  public void add(K key, V value) {
    heap.put(key, value);
  }

  @Override
  public V get(K key) throws MyException {
    if (!heap.containsKey(key)) {
      throw new MyException("Address" + key + " not found");
    }
    return heap.get(key);
  }

  @Override
  public void remove(K key) {
    heap.remove(key);
  }

  @Override
  public boolean isDefined(K key) {
    return heap.containsKey(key);
  }

  @Override
  public void update(K key, V value) throws MyException {
    if (!heap.containsKey(key)) {
      throw new MyException("Address" + key + " not found");
    }
    heap.put(key, value);
  }

  @Override
  public Map<K, V> getContent() {
    return heap;
  }

  @Override
  public void setContent(Map<K, V> newContent) {
    this.heap = newContent;
  }

  @Override
  public int getNextFreeAddress() {
    return nextFreeAddress++;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<K, V> entry : heap.entrySet()) {
      result.append(entry.getKey()).append("->").append(entry.getValue()).append("\n");
    }
    return result.toString();
  }
}
