package com.company.Cat;

import com.company.Plate.Plate;

public class Cat {
    private String name;
    private int appetite;
    private boolean satiety;

    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
        satiety = false;
    }

    public boolean eat(Plate p) {
        satiety = p.decreaseFood(appetite);
        return satiety;
    }

    public void echoSatiety(){
        if(satiety)
            System.out.println(name + " is well-fed cat");
        else
            System.out.println(name + " is hungry cat");
    }

}
