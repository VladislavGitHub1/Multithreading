package com.chernenkov.multithreading.util;

public class IdGeneratorForPier {

        private static int id = 0;

        public static int generateID() {
            return ++id;
        }

}
