package controller;

import exceptions.ExpressionException;
import exceptions.MyException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import model.ADT.MyIHeap;
import model.PrgState;
import model.values.RefValue;
import model.values.Value;
import repository.IRespository;

public class Controller {
  private IRespository repo;
  private ExecutorService executor;

  public Controller(IRespository repo) {
    this.repo = repo;
  }

  public IRespository getRepository() {
    return repo;
  }

  public void setRepository(IRespository repo) {}

  // removing completed programs
  public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
    return inPrgList.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
  }

  // executing one step for all programs concurrently
  public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
    // logging state before execution
    prgList.forEach(
        prg -> {
          try {
            repo.logPrgStateExec(prg);
          } catch (MyException e) {
            System.out.println("Error loading: " + e.getMessage());
          }
        });
    // preparing list for callables(tasks to be executed concurrently)
    List<Callable<PrgState>> callList =
        prgList.stream()
            .map(
                (PrgState p) ->
                    (Callable<PrgState>)
                        (() -> {
                          return p.oneStep();
                        }))
            .collect(Collectors.toList());

    // initializing executor
    if (executor == null || executor.isShutdown()) {
      executor = Executors.newFixedThreadPool(2);
    }

    // executing all callables concurrently and get results
    List<PrgState> newPrgList =
        executor.invokeAll(callList).stream()
            .map(
                future -> {
                  try {
                    return future.get();
                  } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Error during execution: " + e.getMessage());
                    return null;
                  }
                })
            .filter(p -> p != null)
            .collect(Collectors.toList());

    // add newly created threads to the list
    prgList.addAll(newPrgList);

    // adding garbage collector
    if (!prgList.isEmpty()) {
      conservativeGarbageCollector(prgList);
    }

    // logging state after execution
    prgList.forEach(
        prg -> {
          try {
            repo.logPrgStateExec(prg);
          } catch (MyException e) {
            System.out.println("Error logging: " + e.getMessage());
          }
        });
    // updating repository
    repo.setPrgList(prgList);
  }

  //  public PrgState oneStep(PrgState state) throws MyException, ExpressionException {
  //    MyIStack<Stmt> stk = state.getStk();
  //    if (stk.isEmpty()) {
  //      throw new MyException("Empty stack");
  //    }
  //    Stmt currStmt = stk.pop();
  //    return currStmt.execute(state);
  //  }

  // Complete execution of all programs

  public void allStep() throws MyException, ExpressionException, InterruptedException {
    executor = Executors.newFixedThreadPool(2);

    List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

    while (prgList.size() > 0) {
      conservativeGarbageCollector(prgList);

      oneStepForAllPrg(prgList);

      prgList = removeCompletedPrg(repo.getPrgList());
    }
    executor.shutdownNow();

    repo.setPrgList(prgList);
  }

  // safe garbage collector
  private void conservativeGarbageCollector(List<PrgState> prgList) {
    // getting all the addresses referenced in all symbol tables
    List<Integer> allAddresses =
        prgList.stream()
            .flatMap(p -> getAddrFromSymTable(p.getSymTable().getContent().values()).stream())
            .collect(Collectors.toList());

    // get heap from first program as they all share the same heap
    if (prgList.size() > 0) {
      MyIHeap<Integer, Value> heap = prgList.get(0).getHeap();
      heap.setContent(unsafeGarbageCollector(allAddresses, heap.getContent()));
    }
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

  Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
    return heap.entrySet().stream()
        .filter(e -> symTableAddr.contains(e.getKey()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}

//
//  Map<Integer, Value> safeGarbageCollector(
//      Collection<Value> symTableValues, Map<Integer, Value> heap) {
//
//    // 1. Get initial addresses directly from the Symbol Table
//    Set<Integer> reachable = new HashSet<>();
//    Deque<Integer> stack = new ArrayDeque<>();
//
//    for (Value v : symTableValues) {
//      if (v instanceof RefValue rv) {
//        int addr = rv.getAddress();
//        if (heap.containsKey(addr) && reachable.add(addr)) stack.push(addr);
//      }
//    }
//
//    // 2. Recursively follow references IN THE HEAP (safe GC)
//    while (!stack.isEmpty()) {
//      int address = stack.pop();
//      Value heapVal = heap.get(address);
//
//      if (heapVal instanceof RefValue rv) {
//        int innerAddr = rv.getAddress();
//        if (heap.containsKey(innerAddr) && reachable.add(innerAddr)) {
//          stack.push(innerAddr);
//        }
//      }
//    }
//
//    // 3. Build new heap with reachable addresses only
//    return heap.entrySet().stream()
//        .filter(e -> reachable.contains(e.getKey()))
//        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//  }
