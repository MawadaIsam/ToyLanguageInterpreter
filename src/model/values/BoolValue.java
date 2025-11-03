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
    return Boolean.toString(val);
  }

  @Override
  public Type getType() {

    return new BoolType();
  }
}
