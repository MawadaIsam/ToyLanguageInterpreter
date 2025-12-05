package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.PrgState;
import model.exp.Exp;
import model.values.RefValue;
import model.values.Value;

public class WriteHeapStmt implements Stmt {
  private String varName;
  private Exp exp;

  public WriteHeapStmt(String varName, Exp exp) {
    this.varName = varName;
    this.exp = exp;
  }

  @Override
  public PrgState execute(PrgState state) throws MyException, ExpressionException {
    MyIHeap<Integer, Value> heap = state.getHeap();
    MyIDict<String, Value> symTable = state.getSymTable();

    if (!symTable.isDefined(varName)) {
      throw new MyException("Variable " + varName + " is not defined");
    }
    Value varValue = symTable.find(varName);

    if (!(varValue instanceof RefValue)) {
      throw new MyException("Variable " + varName + " is not a ref type");
    }

    RefValue refValue = (RefValue) varValue;
    int address = refValue.getAddress();

    if (!heap.isDefined(address)) {
      throw new MyException("Address " + address + " is not defined in heap");
    }
    Value expValue = exp.eval(symTable, heap);

    if (!expValue.getType().equals(refValue.getLocationType())) {
      throw new MyException("Type mismatch in heap writing");
    }
    heap.update(address, expValue);
    return state;
  }

  @Override
  public String toString() {
    return "Write heap Statement (" + varName + " ," + exp.toString() + ")";
  }
}
