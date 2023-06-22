package com.chernenkov.multithreading.creator;

import com.chernenkov.multithreading.entity.Pier;
import com.chernenkov.multithreading.util.IdGeneratorForPier;

public class PierFactory {
    public static Pier createPier(){
        Pier pier = new Pier(IdGeneratorForPier.generateID());
        return pier;
    }
}
