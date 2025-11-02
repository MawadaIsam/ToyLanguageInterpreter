package model.types;

public class BoolType implements Type {
    String type="bool";
    public BoolType(){
        this.type=type;
    }
    @Override
    public String toString() {
        return type;
    }
    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }
}
