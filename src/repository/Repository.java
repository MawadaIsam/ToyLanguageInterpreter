package repository;

import model.PrgState;

public class Repository implements IRespository {
  private PrgState prg;

  public Repository(PrgState prg) {
    this.prg = prg;
  }

  @Override
  public PrgState getCrtPrg() {
    return prg;
  }
}
