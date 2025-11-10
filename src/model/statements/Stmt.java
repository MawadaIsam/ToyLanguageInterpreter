package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.PrgState;

public interface Stmt {
  PrgState execute(PrgState state) throws MyException, ExpressionException;
}
