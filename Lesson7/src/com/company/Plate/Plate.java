package com.company.Plate;

public class Plate {
    private int food;

    public Plate(int food) {
        this.food = food;
    }

    public boolean decreaseFood(int n) {
        if(food < n) return false;
        food -= n;
        return true;
    }

    public void info() {
        System.out.println("plate: " + food);
    }

    public void addFood(int food){
        this.food += food;
    }

}
