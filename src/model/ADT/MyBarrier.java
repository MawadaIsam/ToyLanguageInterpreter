package model.ADT;

import exceptions.MyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

public class MyBarrier implements MyIBarrier {
  private Map<Integer, Pair<Integer, List<Integer>>> barrier;
  ;
  private int nextFreeAddress;

  public MyBarrier() {
    this.barrier = new HashMap<>();
    this.nextFreeAddress = 1;
  }

  @Override
  public synchronized void add(Integer key, Pair<Integer, List<Integer>> value) {
    barrier.put(key, value);
  }

  @Override
  public synchronized Pair<Integer, List<Integer>> get(Integer key) throws MyException {
    if (!barrier.containsKey(key)) {
      throw new MyException("Barrier Id" + key + " not found");
    }
    return barrier.get(key);
  }

  @Override
  public synchronized boolean isDefined(Integer key) {
    return barrier.containsKey(key);
  }

  @Override
  public synchronized void update(Integer key, Pair<Integer, List<Integer>> value)
      throws MyException {
    if (!barrier.containsKey(key)) {
      throw new MyException("Address" + key + " not found");
    }
    barrier.put(key, value);
  }

  @Override
  public synchronized Map<Integer, Pair<Integer, List<Integer>>> getContent() {
    return barrier;
  }

  @Override
  public synchronized int getNextFreeAddress() {
    return nextFreeAddress++;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<Integer, Pair<Integer, List<Integer>>> entry : barrier.entrySet()) {
      result
          .append(entry.getKey())
          .append("->")
          .append(entry.getValue().getKey())
          .append("->")
          .append(entry.getValue().getValue())
          .append("\n");
    }
    return result.toString();
  }
}
