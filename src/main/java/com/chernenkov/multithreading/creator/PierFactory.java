package com.chernenkov.multithreading.creator;

import com.chernenkov.multithreading.entity.Pier;
import com.chernenkov.multithreading.util.IdGenerator;

public class PierFactory {
    public static Pier createPier(){
        Pier pier = new Pier(IdGenerator.generatePierId());
        return pier;
    }
}
