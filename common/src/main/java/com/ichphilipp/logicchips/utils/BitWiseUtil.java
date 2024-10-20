package com.ichphilipp.logicchips.utils;

/**
 * @author ZZZank
 */
public interface BitWiseUtil {

    static int wrap(boolean[] bits) {
        int value = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                value = value | (1 << i);
            }
        }
        return value;
    }

    static boolean get(int wrapped, int index) {
        return ((wrapped >> index) & 1) != 0;
    }

    static int wrap(boolean first) {
        return first ? 1 : 0;
    }

    static int wrap(boolean first, boolean second) {
        return first ? second ? 3 : 2 : second ? 1 : 0;
    }

    static int wrap(boolean first, boolean second, boolean third) {
        return first
            ? second ? third ? 7 : 6 : third ? 5 : 4
            : second ? third ? 3 : 2 : third ? 1 : 0;
    }
}
