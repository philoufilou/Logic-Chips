package com.ichphilipp.logicchips.api;

@FunctionalInterface
public interface TriInputLogic {

    boolean apply(boolean left, boolean back, boolean right);
}
