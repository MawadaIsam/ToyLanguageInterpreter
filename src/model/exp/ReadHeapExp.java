package model.exp;

import exceptions.ExpressionException;
import exceptions.MyException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class ReadHeapExp implements Exp {
  private Exp exp;

  public ReadHeapExp(Exp exp) {
    this.exp = exp;
  }

  @Override
  public Value eval(MyIDict<String, Value> tbl, MyIHeap<Integer, Value> hp)
      throws ExpressionException, MyException {
    Value value = exp.eval(tbl, hp);
    if (!(value instanceof RefValue)) {
      throw new ExpressionException("Expression is not a ref value");
    }
    RefValue refValue = (RefValue) value;
    int address = refValue.getAddress();

    if (!hp.isDefined(address)) {
      throw new ExpressionException("Address" + address + " is not defined in the heap");
    }
    return hp.get(address);
  }

  @Override
  public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    Type type = exp.typecheck(typeEnv);

    if (type instanceof RefType) {
      RefType refType = (RefType) type;
      return refType.getInner();
    } else {
      throw new MyException("The ReadHeap argument is not a ref type");
    }
  }

  @Override
  public String toString() {
    return "ReadHeapExp (" + exp.toString() + ")";
  }
}
