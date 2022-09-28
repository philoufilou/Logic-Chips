package com.ichphilipp.logicgates.utils;

import com.ichphilipp.logicgates.LogicGates;
import java.util.Objects;
import java.util.function.Function;

public interface TriFunction<A,B,C,R> {
    R apply(A a, B b, C c);

    default <V> TriFunction<A, B, C, V> andThen(
            Function<? super R, ? extends V> after
    ) {
        LogicGates.LOGGER.info(after);
        Objects.requireNonNull(after);
        return (A a, B b, C c) -> after.apply(apply(a, b, c));
    }
}

