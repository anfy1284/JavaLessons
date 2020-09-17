package com.company.Cat;

import com.company.Animal.Animal;

public class Cat extends Animal {

    @Override
    public void setDefault() {
        set(2.0, 200.0, 0.0, "Cat");
    }

    public Cat(String name) {
        super(name);
    }

    public Cat(String name, double jumpHigh, double runDistance) {
        super(name, jumpHigh, runDistance, 0);
    }

    @Override
    public boolean swim(double swimDistance) {
        if (echo) System.out.printf("Cats can't swim!\n");
        return false;
    }
}
