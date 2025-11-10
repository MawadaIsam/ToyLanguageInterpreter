package model.types;

import model.values.IntValue;
import model.values.Value;

public class IntType implements Type {
  String type = "int";

  public IntType() {
    this.type = type;
  }

  @Override
  public String toString() {
    return type;
  }

  @Override
  public boolean equals(Object another) {
    return another instanceof IntType;
  }

  @Override
  public Value defaultValue() {
    return new IntValue(0);
  }
}
