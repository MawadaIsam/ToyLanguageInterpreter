package model.types;

public class IntType implements Type {
    String type="int";
    public IntType(){
        this.type=type;
    }
    @Override
    public String toString(){
        return type;
    }
    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }
}
