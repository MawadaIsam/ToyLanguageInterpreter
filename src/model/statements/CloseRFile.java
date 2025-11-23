package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.MyException;
import java.io.BufferedReader;
import java.io.IOException;
import model.ADT.MyIDict;
import model.PrgState;
import model.exp.Exp;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

public class CloseRFile implements Stmt {
  private Exp exp;

  public CloseRFile(Exp exp) {
    this.exp = exp;
  }

  @Override
  public String toString() {
    return "closeRFile(" + exp + ")";
  }

  @Override
  public PrgState execute(PrgState prg) throws MyException, ExpressionException {
    Value val = exp.eval(prg.getSymTable(), prg.getHeap());
    if (!val.getType().equals(new StringType())) throw new MyException("Expression not string");

    StringValue fileName = (StringValue) val;
    MyIDict<StringValue, BufferedReader> fileTable = prg.getFileTable();

    if (!fileTable.isDefined(fileName)) throw new MyException("File not open");

    BufferedReader br = fileTable.find(fileName);
    try {
      br.close();
      fileTable.remove(fileName);
    } catch (ADTException | IOException e) {
      throw new MyException("IO error while closing ");
    }
    return prg;
  }
}
