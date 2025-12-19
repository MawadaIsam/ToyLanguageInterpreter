package view.command;

import controller.Controller;
import exceptions.MyException;
import model.ADT.MyDict;
import model.ADT.MyIDict;
import model.statements.Stmt;
import model.types.Type;

public class RunExample extends Command {
  private Controller controller;
  private boolean hasBeenExecuted;
  private Stmt originalProgram;

  public RunExample(String key, Stmt stmt, Controller controller) {
    super(key, stmt.toString());
    this.originalProgram = stmt;
    this.controller = controller;
    this.hasBeenExecuted = false;
  }

  @Override
  public void execute() {
    if (hasBeenExecuted) {
      System.out.println("Program has already been executed");
      return;
    }
    try {
      System.out.println("\n=== Type Checking ===");
      MyIDict<String, Type> typeEnv = new MyDict<>();
      originalProgram.typecheck(typeEnv);
      System.out.println(" Type check passed!");

      System.out.println("\n=== Executing Program ===");
      controller.allStep();
      System.out.println("Execution completed successfully!");
      hasBeenExecuted = true;

    } catch (MyException e) {
      System.out.println(e.getMessage());
      hasBeenExecuted = true;
    } catch (Exception e) {
      System.out.println("Unexpected error: " + e.getMessage());
      hasBeenExecuted = true;
    }
  }

  public boolean hasBeenExecuted() {
    return hasBeenExecuted;
  }
}
