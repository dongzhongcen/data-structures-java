package file;

import java.util.Arrays;

class MyArrayList<T> {
    private Object[] elementData;
    private int size;


    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    /*
        在 get、set、remove 之前，先判断下标是否合法
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
    }

    private void ensureCapacity() {
        if (this.size == this.elementData.length) {
            this.elementData = Arrays.copyOf(this.elementData, this.elementData.length * 2);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + this.size
            );
        }
    }

    public T get(int index){
        checkIndex(index);
        return (T) this.elementData[index];
    }

    public T set(int index, T value){
        checkIndex(index);
        T old = (T) this.elementData[index];
        this.elementData[index] = value;
        return old; //返回旧值
    }

    public boolean add(T value){
        ensureCapacity();
        this.elementData[this.size] = value;
        this.size++;
        return true;    //返回操作状态
    }

    public void add(int index, T value){
        checkIndexForAdd(index);
        ensureCapacity();

        for (int i = size - 1; i >= index; i--) {
            this.elementData[i + 1] = this.elementData[i];
        }

        this.elementData[index] = value;
        size++;
    }

    public T  remove(int index){
        checkIndex(index);

        T old = (T) this.elementData[index];
        for (int i = index; i < size - 1; i++) {
            this.elementData[i] = this.elementData[i + 1];
        }

        this.elementData[size - 1] = null;
        size--;

        return old;
    }

}

public class Demo {
    public static void main(String[] args) {

    }
}

