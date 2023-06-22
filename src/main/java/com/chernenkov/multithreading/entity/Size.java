package com.chernenkov.multithreading.entity;

public enum Size {
    SMALL(50), MEDIUM(100), BIG(150);
    public int maxCapacity;

    Size(int maxCapacity){
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
