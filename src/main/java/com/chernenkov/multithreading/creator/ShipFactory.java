package com.chernenkov.multithreading.creator;

import com.chernenkov.multithreading.entity.Ship;
import com.chernenkov.multithreading.entity.Size;
import com.chernenkov.multithreading.util.IdGenerator;

import java.util.Random;

public class ShipFactory {
    static Random random = new Random();
    public static Ship createShip(){
        int currentCapacity = 0;
        int currentId = IdGenerator.generateId();
        Size[] sizes = Size.values();
        Size currentSize = sizes[random.nextInt(sizes.length)];
        int currentMaxCapacity = currentSize.getMaxCapacity();
        boolean currentLoadingUnloading = random.nextBoolean();
        if (currentLoadingUnloading) {
            currentCapacity = 0;
        } else if (!currentLoadingUnloading) {
            currentCapacity = random.nextInt(currentMaxCapacity);
        }
        Ship ship = new Ship(currentId, currentSize, currentMaxCapacity, currentCapacity, currentLoadingUnloading);
        return ship;
    }
}
