package model.exp;

import exceptions.ExpressionException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.values.Value;

public class VarExp implements Exp {
  private String id;

  public VarExp(String id) {
    this.id = id;
  }

  @Override
  public Value eval(MyIDict<String, Value> tbl, MyIHeap<Integer, Value> hp)
      throws ExpressionException {
    return tbl.find(id);
  }

  @Override
  public String toString() {
    return id;
  }
}
