package org.saadMeddiche.utils;

import java.util.SplittableRandom;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class FasterRandom {

    // 7_561ms, 7_667ms, 9_323ms
    private final static SplittableRandom random = new SplittableRandom();

    // 7_604ms, 8_663ms, 9_588ms
    //private final static ThreadLocalRandom random = ThreadLocalRandom.current();

    public static UUID uuid() {
        return new UUID(random.nextLong(), random.nextLong());
    }

    public static int number() {
        return random.nextInt();
    }

    public static long numberAsLong() {
        return random.nextLong();
    }

}
