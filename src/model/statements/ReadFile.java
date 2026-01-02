package model.statements;

import exceptions.ExpressionException;
import exceptions.MyException;
import java.io.BufferedReader;
import java.io.IOException;
import model.ADT.MyIDict;
import model.ADT.MyIHeap;
import model.PrgState;
import model.exp.Exp;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

public class ReadFile implements Stmt {
  private Exp exp;
  private String varName;

  public ReadFile(Exp exp, String varName) {
    this.exp = exp;
    this.varName = varName;
  }

  @Override
  public String toString() {
    return "readFile(" + exp + "," + varName + ")";
  }

  @Override
  public PrgState execute(PrgState prg) throws MyException, ExpressionException {
    MyIDict<String, Value> symTable = prg.getSymTable();
    MyIDict<StringValue, BufferedReader> fileTable = prg.getFileTable();
    MyIHeap<Integer, Value> heap = prg.getHeap();

    if (!symTable.isDefined(varName))
      throw new MyException("Variable " + varName + " is not defined");
    if (!symTable.find(varName).getType().equals(new IntType()))
      throw new MyException("Variable " + varName + " is not an integer");

    Value val = exp.eval(symTable, heap);
    if (!val.getType().equals(new StringType()))
      throw new MyException("Expression is not a string");

    StringValue fileName = (StringValue) val;

    if (!fileTable.isDefined(fileName)) throw new MyException("File " + fileName + " is not open");

    BufferedReader br = fileTable.find(fileName);
    try {
      String line = br.readLine();
      IntValue intVal;
      if (line == null || line.trim().isEmpty()) {
        intVal = new IntValue(0);
      } else
      // try
      {
        intVal = new IntValue(Integer.parseInt(line.trim()));
      } // catch (NumberFormatException e) {
      // throw new MyException("File contains a non-integer value");
      // }

      symTable.update(varName, intVal);

    } catch (IOException e) {
      throw new MyException("IO Error" + e.getMessage());
    } catch (NumberFormatException e) {
      throw new MyException("File Contains a non-integer value");
    }
    return null;
  }

  @Override
  public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
    Type typeExp = exp.typecheck(typeEnv);

    if (!typeExp.equals(new StringType())) {
      throw new MyException("ReadFile: Expression is not a string");
    }

    return typeEnv;
  }
}
