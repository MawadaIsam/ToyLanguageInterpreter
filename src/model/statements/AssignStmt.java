package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
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
    // MyIStack<Stmt> stk = state.getStk();
    MyIDict<String, Value> symTable = state.getSymTable();
    MyIHeap<Integer, Value> heap = state.getHeap();

    if (symTable.isDefined(id)) {
      Value val = exp.eval(symTable, heap);
      Type typeId = (symTable.find(id)).getType();
      if (val.getType().equals(typeId)) {
        symTable.update(id, val);
      } else
        throw new MyException(
            "declared type of variable" + id + "and type of the assigned expression do not match");
    } else throw new MyException("the used variable " + id + " was not declared before");
    return state;
  }

  @Override
  public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    Type typeVar = typeEnv.find(id);
    Type typeExp = exp.typecheck(typeEnv);

    if (typeVar.equals(typeExp)) {
      return typeEnv;
    } else {
      throw new MyException("Assignment: Right hand side and left hand side have different types");
    }
  }
}
