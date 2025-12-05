package repository;

import exceptions.MyException;
import java.io.*;
import java.util.List;
import java.util.Map;
import model.PrgState;
import model.statements.Stmt;
import model.values.StringValue;
import model.values.Value;

public class Repository implements IRespository {
  private PrgState prg;
  private String logFilePath;

  public Repository(PrgState prg, String logFilePath) {
    this.prg = prg;
    this.logFilePath = logFilePath;
  }

  public Repository(PrgState prg) {
    this.prg = prg;
  }

  @Override
  public PrgState getCrtPrg() {
    return prg;
  }

  @Override
  public void logPrgStateExec() throws MyException {
    try (PrintWriter logFile =
        new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {

      logFile.println("ExeStack: ");
      List<Stmt> stackContent = prg.getStk().getReversed();
      for (Stmt stmt : stackContent) {
        logFile.println(stmt.toString());
      }

      logFile.println("SymTable: ");
      Map<String, Value> symTableContent = prg.getSymTable().getContent();
      for (Map.Entry<String, Value> entry : symTableContent.entrySet()) {
        logFile.println(entry.getKey() + "--> " + entry.getValue().toString());
      }
      logFile.println("Out: ");
      List<Value> outContent = prg.getOut().getList();
      for (Value value : outContent) {
        logFile.println(value.toString());
      }
      logFile.println("File Table: ");
      Map<StringValue, BufferedReader> fileTableContent = prg.getFileTable().getContent();
      for (Map.Entry<StringValue, BufferedReader> entry : fileTableContent.entrySet()) {
        logFile.println(entry.getKey() + " --> " + entry.getValue().toString());
      }
      logFile.println("HeapTable: ");
      logFile.println(prg.getHeap().toString());

      logFile.println();

    } catch (IOException e) {
      throw new MyException("error writing to log file: " + e.getMessage());
    }
  }
}
