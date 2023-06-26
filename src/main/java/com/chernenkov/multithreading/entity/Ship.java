package com.chernenkov.multithreading.entity;

import com.chernenkov.multithreading.exception.CustomPortException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.Semaphore;

public class Ship implements Runnable {
    static Logger logger = LogManager.getLogger();
    private int id;
    private final int TIME_TO_LOAD_ONE_CONTAINER = 30;
    private final int TIME_TO_GO_TO_NEAREST_PIER = 100;

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
        Pier pier = port.getPier();
        int timeForGoToPier = 0;
        logger.info("The ship with id " + this.id + " going to pier number " + pier.getId());
        timeForGoToPier = pier.getId() * TIME_TO_GO_TO_NEAREST_PIER;
        try {
            Thread.sleep(timeForGoToPier);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int timeForLoadingUnloading = 0;
        if (forLoading) {
            timeForLoadingUnloading = this.maxCapacity * TIME_TO_LOAD_ONE_CONTAINER;
        } else {
            timeForLoadingUnloading = currentCapacity * TIME_TO_LOAD_ONE_CONTAINER;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return id == ship.id && maxCapacity == ship.maxCapacity && currentCapacity == ship.currentCapacity && forLoading == ship.forLoading && size == ship.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maxCapacity, currentCapacity, size, forLoading);
    }
}
