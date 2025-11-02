package view;

import exceptions.MyException;

public class main {
  public static void main(String[] args) {
    try {
      View view = new View();
      view.start();
    } catch (MyException e) {
      System.out.println(e.getMessage());
    }
  }
}
