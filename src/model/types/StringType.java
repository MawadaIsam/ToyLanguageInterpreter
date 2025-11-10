package model.types;

import model.values.StringValue;
import model.values.Value;

public class StringType implements Type {
  String type = "string";

  public StringType() {
    this.type = type;
  }

  @Override
  public String toString() {
    return type;
  }

  @Override
  public boolean equals(Object another) {
    return another instanceof StringType;
  }

  @Override
  public Value defaultValue() {
    return new StringValue("");
  }
}
