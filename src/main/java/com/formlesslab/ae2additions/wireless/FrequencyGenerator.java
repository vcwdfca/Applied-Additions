package com.formlesslab.ae2additions.wireless;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class FrequencyGenerator {
    private final Random random = new Random();
    private final Set<Long> used = new HashSet<>();

    public synchronized long next() {
        long value;
        do {
            value = this.random.nextLong();
        } while (value == 0 || value == Long.MIN_VALUE || this.used.contains(value) || this.used.contains(-value));
        this.used.add(value);
        this.used.add(-value);
        return value;
    }

    public synchronized void markUsed(long value) {
        if (value == 0 || value == Long.MIN_VALUE) {
            return;
        }
        this.used.add(value);
        this.used.add(-value);
    }
}
