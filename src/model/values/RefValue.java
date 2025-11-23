package model.values;

import model.types.RefType;
import model.types.Type;

public class RefValue implements Value {
  int address;
  Type locationType;

  public RefValue(int address, Type locationType) {
    this.address = address;
    this.locationType = locationType;
  }

  public int getAddress() {
    return address;
  }

  public Type getLocationType() {
    return locationType;
  }

  @Override
  public Type getType() {
    return new RefType(locationType);
  }

  @Override
  public String toString() {
    return "RefValue{" + "address=" + address + ", locationType=" + locationType + '}';
  }

  @Override
  public boolean equals(Value other) {
    if (other instanceof RefValue) {
      RefValue otherRef = (RefValue) other;
      return this.address == otherRef.address && this.locationType.equals(otherRef.locationType);
    }
    return false;
  }
}
