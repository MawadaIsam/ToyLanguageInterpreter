package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.util.Pair;
import model.ADT.MyIBarrier;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.PrgState;
import model.exp.Exp;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class NewBarrierStmt implements Stmt {
  String var;
  Exp exp;
  Lock lock = new ReentrantLock();

  public NewBarrierStmt(String var, Exp exp) {
    this.var = var;
    this.exp = exp;
  }

  @Override
  public PrgState execute(PrgState state) throws MyException, ExpressionException {
    lock.lock();
    try {
      MyIDict<String, Value> symtbl = state.getSymTable();
      MyIHeap<Integer, Value> heap = state.getHeap();
      MyIBarrier barrier = state.getBarrier();

      Value expVal = exp.eval(symtbl, heap);
      if (!expVal.getType().equals(new IntType())) {
        throw new MyException("NewBarrier:Wrong expression type");
      }
      int Nr = ((IntValue) expVal).getValue();
      int location = barrier.getNextFreeAddress();

      barrier.add(location, new Pair<>(Nr, new ArrayList<>()));

      if (symtbl.isDefined(var) && symtbl.find(var).getType().equals(new IntType())) {
        symtbl.update(var, new IntValue(location));
      } else {
        throw new MyException("NewBarrier:Var is not defined");
      }

    } finally {
      lock.unlock();
    }
    return null;
  }

  @Override
  public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    Type vartypr = typeEnv.find(var);
    Type exptype = exp.typecheck(typeEnv);
    if (!exptype.equals(new IntType()) || !vartypr.equals(new IntType())) {
      throw new MyException("NewBarrier:Both Exp and Val should be integers");
    }
    return typeEnv;
  }

  @Override
  public String toString() {
    return "newBarrier(" + var + "," + exp.toString() + ")";
  }
}
