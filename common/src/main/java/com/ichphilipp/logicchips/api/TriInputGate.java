package com.ichphilipp.logicchips.api;

@FunctionalInterface
public interface TriInputGate {

    boolean apply(boolean left, boolean back, boolean right);
}
