package repository;

import exceptions.MyException;
import model.PrgState;

public interface IRespository {
  PrgState getCrtPrg();

  void logPrgStateExec() throws MyException;
}
