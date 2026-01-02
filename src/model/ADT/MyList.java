package model.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
  List<T> list = new ArrayList<T>();

  public MyList(List<T> list) {
    this.list = list;
  }

  public MyList() {
    this.list = new ArrayList<>();
  }

  @Override
  public List<T> getList() {
    return list;
  }

  @Override
  public void add(T value) {
    list.add(value);
  }

  @Override
  public String toString() {
    return list.toString();
  }

  @Override
  public List<T> getAll() {
    return new ArrayList<>(list); // returning a copy;
  }
}
