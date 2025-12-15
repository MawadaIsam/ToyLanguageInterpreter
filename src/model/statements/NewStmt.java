package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.ADT.MyIStack;
import model.PrgState;
import model.exp.Exp;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class NewStmt implements Stmt {
  private String varName;
  private Exp exp;

  public NewStmt(String varName, Exp exp) {
    this.varName = varName;
    this.exp = exp;
  }

  @Override
  public PrgState execute(PrgState state) throws MyException, ExpressionException {
    MyIStack<Stmt> stack = state.getStk();
    MyIDict<String, Value> symTable = state.getSymTable();
    MyIHeap<Integer, Value> heap = state.getHeap();

    if (!symTable.isDefined(varName)) {
      throw new MyException("Variable " + varName + " is not defined");
    }
    Value varValue = symTable.find(varName);
    if (!(varValue.getType() instanceof RefType)) {
      throw new MyException("Variable " + varName + " is not a ref type");
    }

    Value expValue = exp.eval(symTable, heap);
    RefType varType = (RefType) varValue.getType();
    if (!expValue.getType().equals(varType.getInner())) {
      throw new MyException(
          "Type mismatch: expression type does mot match variable's location type ");
    }
    int newAddress = heap.getNextFreeAddress();
    heap.add(newAddress, expValue);

    symTable.update(varName, new RefValue(newAddress, varType.getInner()));
    return state;
  }

  @Override
  public String toString() {
    return " New ( " + varName + ", " + exp.toString() + ")";
  }

  @Override
  public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    Type typeVar = typeEnv.find(varName);
    Type typeExp = exp.typecheck(typeEnv);

    if (typeVar.equals(new RefType(typeExp))) {
      return typeEnv;
    } else {
      throw new MyException("New Stmt: right hand side and left hand side have different types");
    }
  }
}
