package model.statements;

import exceptions.MyException;
import model.ADT.MyIDict;
import model.PrgState;
import model.types.Type;

public class NopStmt implements Stmt {
  @Override
  public PrgState execute(PrgState state) throws MyException {
    return state;
  }

  @Override
  public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    return typeEnv;
  }

  public String toString() {
    return "NopStmt";
  }
}
