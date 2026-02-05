package model.ADT;

import exceptions.MyException;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface MyIBarrier {

  void add(Integer key, Pair<Integer, List<Integer>> value);

  Pair<Integer, List<Integer>> get(Integer key) throws MyException;

  boolean isDefined(Integer key);

  void update(Integer key, Pair<Integer, List<Integer>> value) throws MyException;

  Map<Integer, Pair<Integer, List<Integer>>> getContent();

  int getNextFreeAddress();

  String toString();
}
