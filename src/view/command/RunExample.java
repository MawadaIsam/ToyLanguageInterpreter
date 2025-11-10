package view.command;

import controller.Controller;
import model.statements.Stmt;

public class RunExample extends Command {
  private Controller controller;
  private boolean hasBeenExecuted;

  public RunExample(String key, Stmt stmt, Controller controller) {
    super(key, stmt.toString());
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
      controller.allStep();
      hasBeenExecuted = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public boolean hasBeenExecuted() {
    return hasBeenExecuted;
  }
}
