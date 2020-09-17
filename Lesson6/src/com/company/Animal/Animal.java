package com.company.Animal;

abstract public class Animal {

    protected double jumpHigh;
    protected double runDistance;
    protected double swimDistance;
    protected boolean echo;
    protected String name;

    public void set(double jumpHigh, double runDistance, double swimDistance, String name) {
        this.jumpHigh = jumpHigh;
        this.runDistance = runDistance;
        this.swimDistance = swimDistance;
        this.name = name;
        echo = true;
    }

    abstract public void setDefault();

    public Animal(String name, double jumpHigh, double runDistance, double swimDistance) {
        set(jumpHigh, runDistance, swimDistance, name);
    }

    public Animal(String name) {
        setDefault();
        this.name = name;
    }

    public Animal(){
        setDefault();
    }

    private boolean check(double v1, double v2) {
        return v1 <= v2;
    }

    public boolean jump(double jumpHigh) {
        boolean result = check(jumpHigh, this.jumpHigh);
        if (echo) {
            if (result)
                System.out.printf("%s jumped %5.1f meters up!\n", name, jumpHigh);
            else
                System.out.printf("%s can't jump so high!\n", name, jumpHigh);
        }
        return result;
    }

    public boolean run(double runDistance) {
        boolean result = check(runDistance, this.runDistance);
        if (echo) {
            if (result)
                System.out.printf("%s ran %5.1f meters! Good boy!\n", name, runDistance);
            else
                System.out.printf("%s can't run so far!\n", name, runDistance);
        }
        return result;
    }

    public boolean swim(double swimDistance) {
        boolean result = check(swimDistance, this.swimDistance);
        if (echo) {
            if (result)
                System.out.printf("%s swam %5.1f meters! Very good!\n", name, swimDistance);
            else
                System.out.printf("%s can't swim so long!\n", name, swimDistance);
        }
        return result;
    }

}
