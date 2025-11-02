package model.exp;

import exceptions.MyException;
import model.ADT.MyIDict;
import model.values.Value;

public interface Exp {
    Value eval (MyIDict <String,Value> tbl) throws MyException;
}
