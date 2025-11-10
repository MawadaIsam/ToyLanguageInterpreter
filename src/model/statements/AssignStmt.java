package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIStack;
import model.PrgState;
import model.exp.Exp;
import model.types.Type;
import model.values.Value;

public class AssignStmt implements Stmt {
  String id;
  Exp exp;

  public AssignStmt(String id, Exp exp) {
    this.id = id;
    this.exp = exp;
  }

  public String toString() {
    return id + "=" + exp.toString();
  }

  @Override
  public PrgState execute(PrgState state) throws MyException, ExpressionException {
    MyIStack<Stmt> stk = state.getStk();
    MyIDict<String, Value> symTable = state.getSymTable();

    if (symTable.isDefined(id)) {
      Value val = exp.eval(symTable);
      Type typeId = (symTable.find(id)).getType();
      if (val.getType().equals(typeId)) {
        symTable.update(id, val);
      } else
        throw new MyException(
            "declared type of variable" + id + "and type of the assigned expression do not match");
    } else throw new MyException("the used variable " + id + " was not declared before");
    return state;
  }
}
