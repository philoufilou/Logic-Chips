package com.ichphilipp.logicchips.utils;

// import net.minecraft.util.IStringSerializable;
import com.ichphilipp.logicchips.api.TriInputGate;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum GateFrameTypes implements StringRepresentable {
    empty("empty", (L, B, R) -> false, 0),
    not("not", (L, B, R) -> !B, 1),
    and("and", (L, B, R) -> L && R, 2),
    nand("nand", (L, B, R) -> !(L && R), 2),
    or("or", (L, B, R) -> L || R, 2),
    nor("nor", (L, B, R) -> !(L || R), 2),
    xor("xor", (L, B, R) -> L ^ R, 2),
    xnor("xnor", (L, B, R) -> L == R, 2),
    and_3("and_3", (L, B, R) -> L && B && R, 2),
    nand_3("nand_3", (L, B, R) -> !(L && B && R), 3),
    or_3("or_3", (L, B, R) -> L || B || R, 3),
    nor_3("nor_3", (L, B, R) -> !(L || B || R), 3),
    xor_3("xor_3", (L, B, R) -> ((L ? 1 : 0) + (B ? 1 : 0) + (R ? 1 : 0)) % 2 == 1, 3),
    xnor_3("xnor_3", (L, B, R) -> ((L ? 1 : 0) + (B ? 1 : 0) + (R ? 1 : 0)) % 2 == 0, 3);

    private final String name;
    private final TriInputGate formel;
    private final int canconnect;

    GateFrameTypes(
        String blockstate,
        TriInputGate formel,
        int canconnect
    ) {
        this.name = blockstate;
        this.formel = formel;
        this.canconnect = canconnect;
    }

    public TriInputGate Outputformal() {
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
