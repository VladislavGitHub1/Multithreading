package com.chernenkov.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    static Logger logger = LogManager.getLogger();
    Semaphore semaphore = new Semaphore(0);
    private static Port port;
    private AtomicInteger containerAmount = new AtomicInteger(500);
    private Queue<Pier> piers = new LinkedList<>();
    private static Lock lock = new ReentrantLock(true);
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    Random random = new Random();


    private Port() {
    }

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


    public void addPier(Pier pier) {

        piers.add(pier);
        semaphore.release();
    }

    public Queue<Pier> getPiers() {
        return piers;
    }

    public Pier getPier() {
        lock.lock();
        Pier pier = null;
        try {
            semaphore.acquire();
            pier = piers.poll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
                amountForLoading = random.nextInt(size.getMaxCapacity());
            } else if (size == Size.MEDIUM) {
                amountForLoading = random.nextInt(size.getMaxCapacity());
            } else if (size == Size.LARGE) {
                amountForLoading = random.nextInt(size.getMaxCapacity());
            }
            containerAmount.addAndGet(-amountForLoading);
            train();
            logger.info("The ship with id " + id + " was loaded by " + amountForLoading + " containers");
            return amountForLoading;
        } else if (!forLoading) {
            containerAmount.addAndGet(amount);
            train();
            logger.info("The ship with id " + id + " was unloaded by " + amount + " containers");
            return 0;
        }
        return amountForLoading;
    }

    private void train() {
        if (containerAmount.get() < 300) {
            containerAmount.addAndGet(200);
            logger.info("The train put 200 containers");
        } else if (containerAmount.get() > 3000) {
            containerAmount.addAndGet(-200);
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
