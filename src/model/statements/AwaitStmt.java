package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import java.util.List;
import javafx.util.Pair;
import model.ADT.MyIBarrier;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class AwaitStmt implements Stmt {
  String var;

  public AwaitStmt(String var) {
    this.var = var;
  }

  @Override
  public PrgState execute(PrgState state) throws MyException, ExpressionException {
    MyIDict<String, Value> symtbl = state.getSymTable();
    MyIHeap<Integer, Value> heap = state.getHeap();
    MyIBarrier barrier = state.getBarrier();

    if (!symtbl.isDefined(var)) {
      throw new MyException("Await:Variable " + var + " is not defined");
    }
    Value varValue = symtbl.find(var);
    if (!varValue.getType().equals(new IntType())) {
      throw new MyException("Await:Variable " + var + " is not a number");
    }
    int foundIndex = ((IntValue) varValue).getValue();

    if (!barrier.isDefined(foundIndex)) {
      throw new MyException("Await:Barrier " + foundIndex + " is not defined");
    }
    Pair<Integer, List<Integer>> entry = barrier.get(foundIndex);
    int N1 = entry.getKey();
    List<Integer> List1 = entry.getValue();
    int NL = List1.size();

    if (N1 > NL) {
      if (List1.contains(state.getId())) {
        state.getStk().push(this);
      } else {
        List1.add(state.getId());
        state.getStk().push(this);
      }
    }
    return null;
  }

  @Override
  public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    Type vartypr = typeEnv.find(var);
    if (!vartypr.equals(new IntType())) {
      throw new MyException("Await:Variable is not an Integer");
    }
    return typeEnv;
  }

  @Override
  public String toString() {
    return "Await(" + var + ")";
  }
}
