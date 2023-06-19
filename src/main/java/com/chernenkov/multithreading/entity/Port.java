package com.chernenkov.multithreading.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static Port port;
    private int containerAmount;
    private Queue<Pier> piers = new LinkedList<>();
    private static Lock lock = new ReentrantLock(true);
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    Random random = new Random();

    public static Port getInstance() {
        if (port == null) {
            try {
                lock.lock();
                if (!isCreated.get()) {
                    port = new Port();
                    port.containerAmount = 500;
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return port;
    }

    public int getContainerAmount() {
        return containerAmount;
    }

    public synchronized void increaseContainerAmount(int amont) {
        this.containerAmount = containerAmount + amont;
    }

    public synchronized void decreaseContainerAmount(int amont) {
        this.containerAmount = containerAmount - amont;
    }

    public boolean addPier(Pier pier) {
        return piers.add(pier);
    }

    public Pier getPier() {
        lock.lock();
        Pier pier = null;
        try {
            if (!piers.isEmpty()) {
                pier = piers.poll();
            }
        } finally {
            lock.unlock();
        }
        return pier;
    }

    public int loadingUnloading(int amount, int id, Size size, boolean forLoading) {
        lock.lock();
        int amountForLoading;
        try {
            amountForLoading = 0;
            if (forLoading) {
                if (size == Size.SMALL) {
                    amountForLoading = random.nextInt(50);
                } else if (size == Size.MEDIUM) {
                    amountForLoading = random.nextInt(100);
                } else if (size == Size.BIG) {
                    amountForLoading = random.nextInt(150);
                }
                decreaseContainerAmount(amountForLoading);
                train();
                System.out.println("The ship with id" + id + " was loaded by " + amountForLoading + " containers");
                return amountForLoading;
            } else if (!forLoading) {
                increaseContainerAmount(amount);
                train();
                System.out.println("The ship with id" + id + " was unloaded by " + amount + " containers");
                return 0;
            }
        } finally {
            lock.unlock();
        }
        return amountForLoading;
    }

    private void train() {
        if (containerAmount < 300) {
            containerAmount = containerAmount + 200;
            System.out.println("The train put 200 containers");
        } else if (containerAmount > 3000) {
            containerAmount = containerAmount - 200;
            System.out.println("The train take 200 containers");
        }
    }

}
