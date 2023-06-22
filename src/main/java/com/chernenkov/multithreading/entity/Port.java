package com.chernenkov.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    static Logger logger = LogManager.getLogger();
    private static Port port;
    private AtomicInteger containerAmount = new AtomicInteger(500);
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
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return port;
    }

    public int getContainerAmount() {
        return containerAmount.get();
    }


    public boolean addPier(Pier pier) {
        return piers.add(pier);
    }

    public Queue<Pier> getPiers() {
        return piers;
    }

    public Pier getPier() {
        lock.lock();
        Pier pier = null;
        try {
            if (!piers.isEmpty()) {
                pier = piers.poll();
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            lock.unlock();
        }
        return pier;
    }

    public int loadingUnloading(int amount, int id, Size size, boolean forLoading) {
        int amountForLoading;
            amountForLoading = 0;
            if (forLoading) {
                if (size == Size.SMALL) {
                    amountForLoading = random.nextInt(50);
                } else if (size == Size.MEDIUM) {
                    amountForLoading = random.nextInt(100);
                } else if (size == Size.BIG) {
                    amountForLoading = random.nextInt(150);
                }
                for (int i = 0; i < amountForLoading + 1; i++) {
                    containerAmount.decrementAndGet();
                }
                train();
                logger.info("The ship with id " + id + " was loaded by " + amountForLoading + " containers");
                return amountForLoading;
            } else if (!forLoading) {
                for (int i = 0; i < amountForLoading + 1; i++) {
                    containerAmount.incrementAndGet();
                }
                train();
                logger.info("The ship with id " + id + " was unloaded by " + amount + " containers");
                return 0;
            }
        return amountForLoading;
    }

    private void train() {
        if (containerAmount.get() < 300) {
            while (containerAmount.get() < 500) {
                containerAmount.incrementAndGet();
            }
            logger.info("The train put 200 containers");
        } else if (containerAmount.get() > 3000) {
            while (containerAmount.get()>2500) {
                containerAmount.decrementAndGet();
            }
            logger.info("The train take 200 containers");
        }
    }

    public void capacityChecker() {
        Thread portCapacity = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    logger.info("Now in port " + port.getContainerAmount() + " containers");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        portCapacity.setDaemon(true);
        portCapacity.start();

    }
}
