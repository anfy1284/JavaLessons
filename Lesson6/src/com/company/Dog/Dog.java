package com.company.Dog;

import com.company.Animal.Animal;

public class Dog extends Animal {

    @Override
    public void setDefault() {
        set(0.5, 500.0, 10.0, "Dog");
    }

    public Dog(String name) {
        super(name);
    }

    public Dog(String name, double jumpHigh, double runDistance, double swimDistance) {
        super(name, jumpHigh, runDistance, swimDistance);
    }

}
