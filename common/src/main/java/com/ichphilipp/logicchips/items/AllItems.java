package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.utils.GateFrameTypes;
import java.util.function.Supplier;
import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class AllItems {

    public static final DeferredRegister<Item> ITEM_REG = DeferredRegister.create(LogicChips.MOD_ID,
            Registry.ITEM_REGISTRY);
    // base chip
    public static final Supplier<Item> CHIP = ITEM_REG.register(
            "chip",
            () -> new Item(new Item.Properties().tab(LogicChips.ITEM_GROUP)));
    // 2-inputs gate
    public static final Supplier<Item> NOT_GATE = ITEM_REG.register(
            "not_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.not));
    public static final Supplier<Item> AND_GATE = ITEM_REG.register(
            "and_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.and));
    public static final Supplier<Item> NAND_GATE = ITEM_REG.register(
            "nand_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.nand));
    public static final Supplier<Item> OR_GATE = ITEM_REG.register(
            "or_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.or));
    public static final Supplier<Item> NOR_GATE = ITEM_REG.register(
            "nor_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.nor));
    public static final Supplier<Item> XOR_GATE = ITEM_REG.register(
            "xor_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.xor));
    public static final Supplier<Item> XNOR_GATE = ITEM_REG.register(
            "xnor_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.xnor));
    // 3-inputs gate
    public static final Supplier<Item> AND_GATE_3 = ITEM_REG.register(
            "and_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.and_3));
    public static final Supplier<Item> NAND_GATE_3 = ITEM_REG.register(
            "nand_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.nand_3));
    public static final Supplier<Item> OR_GATE_3 = ITEM_REG.register(
            "or_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.or_3));
    public static final Supplier<Item> NOR_GATE_3 = ITEM_REG.register(
            "nor_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.nor_3));
    public static final Supplier<Item> XOR_GATE_3 = ITEM_REG.register(
            "xor_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.xor_3));
    public static final Supplier<Item> XNOR_GATE_3 = ITEM_REG.register(
            "xnor_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
                    GateFrameTypes.xnor_3));

}
