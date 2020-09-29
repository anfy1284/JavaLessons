package ru.geekbrains.java_two.lesson_a.online;

class StackItem{
    Object value;
    StackItem next;

    StackItem(Object value, StackItem next){
        this.value = value;
        this.next = next;
    }

}

public class MyStack {
    private StackItem firstItem;
    private int length;

    //Для обхода
    private StackItem currentItem;

    MyStack(){
        length = 0;
        firstItem = null;
        reset();
    }

    void reset(){
        currentItem = null;
    }

    public boolean next(){
        if(currentItem == null){
            currentItem = firstItem;
            return true;
        }
        currentItem = currentItem.next;
        if(currentItem == null){
            return false;
        }
        return true;
    }

    public int getLength(){
        return length;
    }

    public void push(Object value){
        StackItem newItem = new StackItem(value, firstItem);
        firstItem = newItem;
        length++;
    }

    public Object pop(){
        if(length == 0) return null;

        Object value = firstItem.value;
        firstItem = firstItem.next;
        length--;
        return value;
    }

    public Object getCurrentValue(){
        return currentItem.value;
    }

}
