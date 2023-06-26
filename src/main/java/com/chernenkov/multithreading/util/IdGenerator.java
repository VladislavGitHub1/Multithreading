package com.chernenkov.multithreading.util;

public class IdGenerator {
    private static int id = 0;
    private static int pierId = 0;
    public static int generateId() {
        return ++id;
    }


    public static int generatePierId() {
        return ++pierId;
    }
}
