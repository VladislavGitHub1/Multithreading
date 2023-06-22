package com.chernenkov.multithreading.entity;

import com.chernenkov.multithreading.util.IdGenerator;

import java.util.Random;

public class Ship implements Runnable {
    private int id;
    private int maxCapacity;
    private int currentCapacity;
    private Size size;

    private boolean forLoading;

    public Ship(int id,Size size, int maxCapacity, int currentCapacity, boolean forLoading) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
        this.size = size;
        this.forLoading = forLoading;
    }

    @Override
    public void run() {
        Port port = Port.getInstance();
        Pier pier = null;
        pier = port.getPier();
        int timeForGoToPier = 0;
            System.out.println("The ship going to pier number" + pier.getId());
           timeForGoToPier = pier.getId() * 100;
            try {
                Thread.sleep(timeForGoToPier);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int timeForLoadingUnloading = 0;
            if (forLoading) {
                timeForLoadingUnloading = this.maxCapacity * 30;
            } else {
                timeForLoadingUnloading = currentCapacity * 30;
            }
            currentCapacity = port.loadingUnloading(currentCapacity, id, size, forLoading);
            try {
                Thread.sleep(timeForLoadingUnloading);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            port.addPier(pier);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("id=").append(id);
        sb.append(", maxCapacity=").append(maxCapacity);
        sb.append(", currentCapacity=").append(currentCapacity);
        sb.append(", size=").append(size);
        sb.append(", forLoading=").append(forLoading);
        sb.append('}');
        return sb.toString();
    }
}
