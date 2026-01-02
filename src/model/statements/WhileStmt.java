package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.ADT.MyIStack;
import model.PrgState;
import model.exp.Exp;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class WhileStmt implements Stmt {
  Exp exp;
  Stmt whileS;

  public WhileStmt(Exp exp, Stmt whileS) {
    this.exp = exp;
    this.whileS = whileS;
  }

  @Override
  public PrgState execute(PrgState state) throws MyException, ExpressionException {
    MyIDict<String, Value> symTable = state.getSymTable();
    MyIHeap<Integer, Value> heap = state.getHeap();
    MyIStack<Stmt> stack = state.getStk();

    Value condValue = exp.eval(symTable, heap);

    if (!(condValue.getType() instanceof BoolType))
      throw new MyException("While condition is not a boolean");
    BoolValue boolValue = (BoolValue) condValue;

    if (boolValue.getValue()) {
      stack.push(new WhileStmt(exp, whileS));
      stack.push(whileS);
    }
    return null;
  }

  @Override
  public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    Type typeExp = exp.typecheck(typeEnv);

    if (typeExp instanceof BoolType) {
      whileS.typecheck(typeEnv);
      return typeEnv;
    } else {
      throw new MyException("While condition is not a boolean");
    }
  }

  @Override
  public String toString() {
    return "While{" + exp.toString() + "}" + whileS.toString();
  }
}
