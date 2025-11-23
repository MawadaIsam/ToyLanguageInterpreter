package model.exp;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.values.Value;

public interface Exp {
  Value eval(MyIDict<String, Value> tbl, MyIHeap<Integer, Value> hp)
      throws ExpressionException, MyException;
}
