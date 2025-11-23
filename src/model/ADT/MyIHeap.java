package model.ADT;

import exceptions.MyException;
import java.util.Map;

public interface MyIHeap <K, V> {

    void add(K key, V value);
    V get(K key) throws MyException;
    boolean isDefined(K key) ;
    void remove(K key) ;
    void update(K key, V value) throws MyException;
    Map<K, V> getContent();
    void setContent(Map<K, V> newContent) ;
    int getNextFreeAddress();
    String toString();
}
