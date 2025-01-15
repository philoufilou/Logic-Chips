package com.ichphilipp.logicchips.utils;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.Collection;
import java.util.Objects;

/**
 * @author ZZZank
 */
@UtilityClass
public class Asser {

    public void t(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public void t(boolean condition, String message, Object... args) {
        if (!condition) {
            throw new AssertionError(String.format(message, args));
        }
    }

    public void tEqual(Object a, Object b, String message) {
        if (!Objects.equals(a, b)) {
            throw new AssertionError(message);
        }
    }

    public void tEqual(Object a, Object b) {
        tEqual(a, b, String.format("'%s' and '%s' are not equal", a, b));
    }

    public <T> T tNotNull(T value, String name) {
        if (value == null) {
            throw new NullPointerException("'" + name + "' must not be null");
        }
        return value;
    }

    public <T extends Iterable<?>> T tNotNullAll(T collection, String name) {
        tNotNull(collection, name);
        val elementName = "element in " + name;
        for (val o : collection) {
            tNotNull(o, elementName);
        }
        return collection;
    }

    public <T extends Collection<?>> T tNotEmpty(T value, String name) {
        tNotNull(value, name);
        if (value.isEmpty()) {
            throw new NullPointerException("'" + name + "' must not be empty");
        }
        return value;
    }

    public <T extends CharSequence> T tNotEmpty(T value, String name) {
        tNotNull(value, name);
        if (value.isEmpty()) {
            throw new NullPointerException("'" + name + "' must not be empty");
        }
        return value;
    }
}
