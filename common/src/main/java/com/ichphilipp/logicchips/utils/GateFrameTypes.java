package com.ichphilipp.logicchips.utils;

// import net.minecraft.util.IStringSerializable;
import com.ichphilipp.logicchips.api.TriBoolLogic;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum GateFrameTypes implements StringRepresentable {
    EMPTY((L, B, R) -> false, 0),
    NOT((L, B, R) -> !B, 1),
    AND((L, B, R) -> L && R, 2),
    NAND((L, B, R) -> !(L && R), 2),
    OR((L, B, R) -> L || R, 2),
    NOR((L, B, R) -> !(L || R), 2),
    XOR((L, B, R) -> L ^ R, 2),
    XNOR((L, B, R) -> L == R, 2),
    AND_3((L, B, R) -> L && B && R, 2),
    NAND_3((L, B, R) -> !(L && B && R), 3),
    OR_3((L, B, R) -> L || B || R, 3),
    NOR_3((L, B, R) -> !(L || B || R), 3),
    XOR_3((L, B, R) -> ((L ? 1 : 0) + (B ? 1 : 0) + (R ? 1 : 0)) % 2 == 1, 3),
    XNOR_3((L, B, R) -> ((L ? 1 : 0) + (B ? 1 : 0) + (R ? 1 : 0)) % 2 == 0, 3);

    private final String key;
    private final TriBoolLogic logic;
    private final int canConnect;

    GateFrameTypes(
        TriBoolLogic logic,
        int canConnect
    ) {
        this.key = this.name().toLowerCase();
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
    public String toString() {
        return this.key;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.key;
    }
}
