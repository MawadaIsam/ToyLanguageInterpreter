package model.exp;

import exceptions.ExpressionException;
import model.ADT.MyIDict;
import model.values.Value;

public interface Exp {
    Value eval (MyIDict <String,Value> tbl) throws ExpressionException;
}
