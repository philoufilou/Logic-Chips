package com.ichphilipp.logicchips.utils;

import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.NotNull;

public enum GateFrameTypes implements StringRepresentable {


    Empty("empty",(L, B, R) -> false, 0),
    not_gate("not",(L, B, R) -> !B, 1),
    and_gate("and",(L, B, R) -> L && R, 2),
    nand_gate("nand",(L, B, R) -> !(L && R), 2),
    or_gate("or",(L, B, R) -> L || R, 2),
    nor_gate("nor",(L, B, R) -> !(L || R), 2),
    xor_gate("xor",(L, B, R) -> L ^ R, 2),
    xnor_gate("xnor",(L, B, R) -> L == R, 2),
    and_gate_3("and_3",(L, B, R) -> L && B && R, 2),
    nand_gate_3("nand_3",(L, B, R) -> !(L && B && R), 3),
    or_gate_3("or_3",(L, B, R) -> L || B || R, 3),
    nor_gate_3("nor_3",(L, B, R) -> !(L || B || R), 3),
    xor_gate_3("xor_3",(L, B, R) ->  ((L ? 1 : 0) + (B ? 1 : 0) + (R ? 1 : 0)) % 2 == 1, 3),
    xnor_gate_3("xnor_3",(L, B, R) -> ((L ? 1 : 0) + (B ? 1 : 0) + (R ? 1 : 0)) % 2 == 0, 3);

    private final String name;
    private final TriFunction<Boolean,Boolean,Boolean,Boolean> formel;
    private final int canconnect;

    GateFrameTypes(String blockstate, TriFunction<Boolean,Boolean,Boolean,Boolean> formel, int canconnect) {
        this.name = blockstate;
        this.formel = formel;
        this.canconnect = canconnect;
    }
    public TriFunction<Boolean,Boolean,Boolean,Boolean> Outputformal() {
        return this.formel;
    }
    public int canConnectTo() {
        return this.canconnect;
    }




    public String toString() {
        return this.name;
    }
    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
