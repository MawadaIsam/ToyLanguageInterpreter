package repository;

import exceptions.MyException;
import java.util.List;
import model.PrgState;

public interface IRespository {

  List<PrgState> getPrgList();

  void setPrgList(List<PrgState> prgList);

  void logPrgStateExec(PrgState prg) throws MyException;
}
