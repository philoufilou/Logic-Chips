package com.ichphilipp.logicchips.utils;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum GateFrameTypes implements IStringSerializable {
    Empty("empty"),
    NOT("not"),
    AND("and"),
    NAND("nand"),
    OR("or"),
    NOR("nor"),
    XOR("xor"),
    XNOR("xnor"),
    AND_3("and_3"),
    NAND_3("nand_3"),
    OR_3("or_3"),
    NOR_3("nor_3"),
    XOR_3("xor_3"),
    XNOR_3("xnor_3");

    private final String name;

    GateFrameTypes(String p_i49337_3_) {
        this.name = p_i49337_3_;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}