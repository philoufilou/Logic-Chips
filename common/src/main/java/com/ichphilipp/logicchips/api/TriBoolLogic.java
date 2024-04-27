package com.ichphilipp.logicchips.api;

@FunctionalInterface
public interface TriBoolLogic {

    boolean apply(boolean left, boolean back, boolean right);
}
