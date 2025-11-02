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
        return Integer.toString(val);
    }
    @Override
    public Type getType() {
        return new IntType();
    }
}
