package model.values;

import model.types.BoolType;
import model.types.Type;

public class BoolValue implements Value {
  private boolean val;

  public BoolValue(boolean val) {
    this.val = val;
  }

  public boolean getValue() {
    return val;
  }

  public String toString() {
    return String.valueOf(val);
  }

  @Override
  public Type getType() {

    return new BoolType();
  }

  @Override
  public boolean equals(Value obj) {
    if (obj instanceof BoolValue) return ((BoolValue) obj).val == this.val;
    return false;
  }
}
