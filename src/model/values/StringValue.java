package model.values;

import model.types.StringType;
import model.types.Type;

public class StringValue implements Value {
  String value;

  public StringValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public String toString() {
    return value;
  }

  @Override
  public Type getType() {
    return new StringType();
  }

  @Override
  public boolean equals(Value obj) {
    if (obj instanceof StringValue) return ((StringValue) obj).getValue().equals(this.value);
    return false;
  }
}
