package model.statements;

import exceptions.MyException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import model.ADT.MyIDict;
import model.PrgState;
import model.exp.Exp;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

public class OpenRFile implements Stmt {
  private Exp exp;

  public OpenRFile(Exp exp) {
    this.exp = exp;
  }

  @Override
  public PrgState execute(PrgState prg) throws MyException {
      Value value=exp.eval(prg.getSymTable());
      if(!value.getType().equals(new  StringType()))
          throw new MyException("Expression is not a string");
      StringValue fileName =(StringValue)value;
      MyIDict<StringValue,BufferedReader> fileTable=prg.getFileTable();

      if(fileTable.isDefined(fileName))
          throw new MyException("File already opened");

      try{
          BufferedReader br = new BufferedReader(new FileReader(fileName.getValue()));
          fileTable.update(fileName,br);
      }catch(IOException e){
          throw new MyException("Cannot open file"+fileName.getValue()+" : "+e.getMessage());
      }
      return prg;
  }
  @Override
    public String toString(){
      return "openRFile("+exp+")";
  }
}
