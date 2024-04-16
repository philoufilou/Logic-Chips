package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.utils.GateFrameTypes;
import java.util.function.Supplier;
import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class AllItems {

    public static void init() { ITEM_REG.register();
    }

    public static final DeferredRegister<Item> ITEM_REG = DeferredRegister.create(
        LogicChips.MOD_ID,
        Registry.ITEM_REGISTRY
    );
    // base chip
    public static final Supplier<Item> CHIP;
    // 2-inputs gate
    public static final Supplier<Item> NOT_GATE;
    public static final Supplier<Item> AND_GATE;
    public static final Supplier<Item> NAND_GATE;
    public static final Supplier<Item> OR_GATE;
    public static final Supplier<Item> NOR_GATE;
    public static final Supplier<Item> XOR_GATE;
    public static final Supplier<Item> XNOR_GATE;
    // 3-inputs gate
    public static final Supplier<Item> AND_GATE_3;
    public static final Supplier<Item> NAND_GATE_3;
    public static final Supplier<Item> OR_GATE_3;
    public static final Supplier<Item> NOR_GATE_3;
    public static final Supplier<Item> XOR_GATE_3;
    public static final Supplier<Item> XNOR_GATE_3;

    //prettier-ignore
    static {
        CHIP = ITEM_REG.register("chip", () -> new Item(new Item.Properties().tab(LogicChips.ITEM_GROUP)));
        NOT_GATE = ITEM_REG.register(
            "not_gate",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.not));
        AND_GATE = ITEM_REG.register(
            "and_gate",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.and));
        NAND_GATE = ITEM_REG.register(
            "nand_gate",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.nand));
        OR_GATE = ITEM_REG.register(
            "or_gate",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.or));
        NOR_GATE = ITEM_REG.register(
            "nor_gate",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.nor));
        XOR_GATE = ITEM_REG.register(
            "xor_gate",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.xor));
        XNOR_GATE = ITEM_REG.register(
            "xnor_gate",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.xnor));

        AND_GATE_3 = ITEM_REG.register(
            "and_gate_3",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.and_3));
        NAND_GATE_3 = ITEM_REG.register(
            "nand_gate_3",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.nand_3));
        OR_GATE_3 = ITEM_REG.register(
            "or_gate_3",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.or_3));
        NOR_GATE_3 = ITEM_REG.register(
            "nor_gate_3",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.nor_3));
        XOR_GATE_3 = ITEM_REG.register(
            "xor_gate_3",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.xor_3));
        XNOR_GATE_3 = ITEM_REG.register(
            "xnor_gate_3",
            () -> new Chip(
                new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                GateFrameTypes.xnor_3));
    }
}
