package controller;

import exceptions.ExpressionException;
import exceptions.MyException;
import java.util.*;
import java.util.stream.Collectors;
import model.ADT.MyIStack;
import model.PrgState;
import model.statements.Stmt;
import model.values.RefValue;
import model.values.Value;
import repository.IRespository;

public class Controller {
  private IRespository repo;

  public Controller(IRespository repo) {
    this.repo = repo;
  }

  public PrgState oneStep(PrgState state) throws MyException, ExpressionException {
    MyIStack<Stmt> stk = state.getStk();
    if (stk.isEmpty()) {
      throw new MyException("Empty stack");
    }
    Stmt currStmt = stk.pop();
    return currStmt.execute(state);
  }

  public void allStep() throws MyException, ExpressionException {
    PrgState prg = repo.getCrtPrg();
    repo.logPrgStateExec();

    while (!prg.getStk().isEmpty()) {
      oneStep(prg);
      repo.logPrgStateExec();
      prg.getHeap()
          .setContent(
              unsafeGarbageCollector(
                  getAddrFromSymTable(prg.getSymTable().getContent().values()),
                  prg.getHeap().getContent()));
      repo.logPrgStateExec();
    }
  }

  Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
    return heap.entrySet().stream()
        .filter(e -> symTableAddr.contains(e.getKey()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
    return symTableValues.stream()
        .filter(v -> v instanceof RefValue)
        .map(
            v -> {
              RefValue v1 = (RefValue) v;
              return v1.getAddress();
            })
        .collect(Collectors.toList());
  }






  Map<Integer, Value> safeGarbageCollector(
      Collection<Value> symTableValues, Map<Integer, Value> heap) {

    // 1. Get initial addresses directly from the Symbol Table
    Set<Integer> reachable = new HashSet<>();
    Deque<Integer> stack = new ArrayDeque<>();

    for (Value v : symTableValues) {
      if (v instanceof RefValue rv) {
        int addr = rv.getAddress();
        if (heap.containsKey(addr) && reachable.add(addr)) stack.push(addr);
      }
    }

    // 2. Recursively follow references IN THE HEAP (safe GC)
    while (!stack.isEmpty()) {
      int address = stack.pop();
      Value heapVal = heap.get(address);

      if (heapVal instanceof RefValue rv) {
        int innerAddr = rv.getAddress();
        if (heap.containsKey(innerAddr) && reachable.add(innerAddr)) {
          stack.push(innerAddr);
        }
      }
    }

    // 3. Build new heap with reachable addresses only
    return heap.entrySet().stream()
        .filter(e -> reachable.contains(e.getKey()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
