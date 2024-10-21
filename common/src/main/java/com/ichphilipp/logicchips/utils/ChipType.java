package com.ichphilipp.logicchips.utils;

import com.ichphilipp.logicchips.api.TriBoolLogic;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * minecraft seems not accepting upper case name
 */
public enum ChipType implements StringRepresentable, TriBoolLogic {
    empty((L, B, R) -> false, 0),
    not((L, B, R) -> !B, 1),
    and((L, B, R) -> L && R, 2),
    nand((L, B, R) -> !(L && R), 2),
    or((L, B, R) -> L || R, 2),
    nor((L, B, R) -> !(L || R), 2),
    xor((L, B, R) -> L ^ R, 2),
    xnor((L, B, R) -> L == R, 2),
    and_3((L, B, R) -> L && B && R, 2),
    nand_3((L, B, R) -> !(L && B && R), 3),
    or_3((L, B, R) -> L || B || R, 3),
    nor_3((L, B, R) -> !(L || B || R), 3),
    xor_3((L, B, R) -> ((L ? 1 : 0) + (B ? 1 : 0) + (R ? 1 : 0)) % 2 == 1, 3),
    xnor_3((L, B, R) -> ((L ? 1 : 0) + (B ? 1 : 0) + (R ? 1 : 0)) % 2 == 0, 3),
    dynamic(null, 3)
    ;

    private final TriBoolLogic logic;
    private final int canConnect;

    ChipType(TriBoolLogic logic, int canConnect) {
        this.logic = logic;
        this.canConnect = canConnect;
    }

    public TriBoolLogic logic() {
        return this.logic;
    }

    public int canConnectTo() {
        return this.canConnect;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name();
    }

    @Override
    public boolean apply(boolean left, boolean middle, boolean right) {
        return this.logic.apply(left, middle, right);
    }
}
