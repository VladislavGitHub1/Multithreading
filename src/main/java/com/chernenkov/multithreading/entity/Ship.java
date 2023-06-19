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
        if (!(pier == null)) {
            if (forLoading) {
                port.loading(this.size, this.id);
                currentCapacity = maxCapacity;
                System.out.println("Current containers amount in port: " + port.getContainerAmount());
            } else if (!forLoading) {
                port.unloading(currentCapacity, this.id);
                currentCapacity = 0;
                System.out.println("Current containers amount in port: " + port.getContainerAmount());
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            port.addPier(pier);
        } else if (pier == null) {
            try {
                Thread.sleep(1000);
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
