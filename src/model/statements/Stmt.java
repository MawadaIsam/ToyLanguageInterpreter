package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.PrgState;
import model.types.Type;

public interface Stmt {
  PrgState execute(PrgState state) throws MyException, ExpressionException;

  MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException;
}
