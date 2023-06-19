package com.chernenkov.multithreading.entity;

import com.chernenkov.multithreading.util.IdGenerator;

import java.util.Random;

public class Ship implements Runnable {
    private int id;
    private int maxCapacity;
    private int currentCapacity;
    private Size size;

    private boolean forLoading;
    Random random = new Random();

    public Ship() {
        this.id = IdGenerator.generateID();
        this.forLoading = random.nextBoolean();
        Size[] sizes = Size.values();
        this.size = sizes[random.nextInt(sizes.length)];
        if (size == Size.SMALL) {
            this.maxCapacity = 50;
        } else if (size == Size.MEDIUM) {
            this.maxCapacity = 100;
        } else if (size == Size.BIG) {
            this.maxCapacity = 150;
        }
        if (forLoading) {
            currentCapacity = 0;
        } else if (!forLoading) {
            currentCapacity = random.nextInt(maxCapacity);

        }

    }

    @Override
    public void run() {
        Port port = Port.getInstance();
        Pier pier = null;
        pier = port.getPier();
        int timeForGoToPier = 0;
        if (!(pier == null)) {
            System.out.println("The ship going to pier number" + pier.getId());
            switch (pier.getId()) {
                case 1:
                    timeForGoToPier = 1000;
                case 2:
                    timeForGoToPier = 2000;
                case 3:
                    timeForGoToPier = 3000;
            }
            try {
                Thread.sleep(timeForGoToPier);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int timeForLoadingUnloading = 0;
            if (currentCapacity == 0) {
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
        } else if (pier == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.run();
        }
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
