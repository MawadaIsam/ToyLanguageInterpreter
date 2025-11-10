package model.values;

import model.types.IntType;
import model.types.Type;

public class IntValue implements Value {
  private int val;

  public IntValue(int val) {
    this.val = val;
  }

  public int getValue() {
    return val;
  }

  public String toString() {
    return String.valueOf(val);
  }

  @Override
  public Type getType() {
    return new IntType();
  }

  @Override
  public boolean equals(Value obj) {
    if (obj instanceof IntValue) return ((IntValue) obj).val == this.val;
    return false;
  }
}
