package model.exp;

import exceptions.MyException;
import model.ADT.MyIDict;
import model.values.Value;

public class VarExp implements Exp {
  private String id;

  public VarExp(String id) {
    this.id = id;
  }

  @Override
  public Value eval(MyIDict<String, Value> tbl) throws MyException {
    return tbl.find(id);
  }

  @Override
  public String toString() {
    return id;
  }
}
