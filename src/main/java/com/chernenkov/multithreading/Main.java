package com.chernenkov.multithreading;

import com.chernenkov.multithreading.creator.PierFactory;
import com.chernenkov.multithreading.creator.ShipFactory;
import com.chernenkov.multithreading.entity.Pier;
import com.chernenkov.multithreading.entity.Port;
import com.chernenkov.multithreading.entity.Ship;

public class Main {
    public static void main(String[] args) {
        Port port = Port.getInstance();
        port.capacityChecker();
        for(int i=0;i<4; i++){
            Pier pier = PierFactory.createPier();
            port.addPier(pier);
        }
        for (int i = 1; i < 20; i++) {
            Ship ship = ShipFactory.createShip();
            Thread thread = new Thread(ship);
            System.out.println(ship);
            thread.start();
        }
    }
}