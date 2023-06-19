package com.chernenkov.multithreading;

import com.chernenkov.multithreading.entity.Pier;
import com.chernenkov.multithreading.entity.Port;
import com.chernenkov.multithreading.entity.Ship;

public class Main {
    public static void main(String[] args) {
        Port port = Port.getInstance();
        Pier pier1 = new Pier(1);
        Pier pier2 = new Pier(2);
        Pier pier3 = new Pier(3);
        port.addPier(pier1);
        port.addPier(pier2);
        port.addPier(pier3);
        for (int i = 1; i < 6; i++) {
            Ship ship = new Ship();
            Thread thread = new Thread(ship);
            System.out.println(ship);
            thread.start();
        }
    }
}