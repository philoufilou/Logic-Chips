package com.ichphilipp.logicchips.allitems;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.utils.GateFrameTypes;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AllItems {
    private static final int MAX_STACK_SIZE_CHIPS = LogicChips.config.max_stack_size_chips.get();
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LogicChips.MOD_ID);
    // -------------------------------------------------------------------------------------------------------
    public static final RegistryObject<Item> CHIP = ITEMS.register("chip",
            () -> new Item(new Item.Properties().tab(LogicChips.ITEM_GROUP)));
    // -------------------------------------------------------------------------------------------------------
    public static final RegistryObject<Item> NOT_GATE = ITEMS.register("not_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.NOT,
                    (L, B, R) -> !B,
                    new int[]{0,1,0}
            )
    );
    public static final RegistryObject<Item> AND_GATE = ITEMS.register("and_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.AND,
                    (L, B, R) -> L && R,
                    new int[]{1,0,1}
            )
    );
    public static final RegistryObject<Item> NAND_GATE = ITEMS.register("nand_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.NAND,
                    (L, B, R) -> !(L && R),
                    new int[]{1,0,1}
            )
    );
    public static final RegistryObject<Item> OR_GATE = ITEMS.register("or_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.OR,
                    (L, B, R) -> L || R,
                    new int[]{1,0,1}
            )
    );
    public static final RegistryObject<Item> NOR_GATE = ITEMS.register("nor_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.NOR,
                    (L, B, R) -> !(L || R),
                    new int[]{1,0,1}
            )
    );
    public static final RegistryObject<Item> XOR_GATE = ITEMS.register("xor_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.XOR,
                    (L, B, R) -> L ^ R,
                    new int[]{1,0,1}
            )
    );
    public static final RegistryObject<Item> XNOR_GATE = ITEMS.register("xnor_gate",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.XNOR,
                    (L, B, R) -> L == R,
                    new int[]{1,0,1}
            )
    );
    // -------------------------------------------------------------------------------------------------------
    public static final RegistryObject<Item> AND_GATE_3 = ITEMS.register("and_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.AND_3,
                    (L, B, R) -> L && B && R,
                    new int[]{1,1,1}
            )
    );
    public static final RegistryObject<Item> NAND_GATE_3 = ITEMS.register("nand_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.NAND_3,
                    (L, B, R) -> !(L && B && R),
                    new int[]{1,1,1}
            )
    );
    public static final RegistryObject<Item> OR_GATE_3 = ITEMS.register("or_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.OR_3,
                    (L, B, R) -> L || B || R,
                    new int[]{1,1,1}
            )
    );
    public static final RegistryObject<Item> NOR_GATE_3 = ITEMS.register("nor_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.NOR_3,
                    (L, B, R) -> !(L || B || R),
                    new int[]{1,1,1}
            )
    );
    public static final RegistryObject<Item> XOR_GATE_3 = ITEMS.register("xor_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.XOR_3,
                    (L, B, R) ->  ((L ? 1 : 0) + (B ? 1 : 0) + (R ? 1 : 0)) % 2 == 1,
                    new int[]{1,1,1}
            )
    );
    public static final RegistryObject<Item> XNOR_GATE_3 = ITEMS.register("xnor_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.XNOR_3,
                    (L, B, R) -> ((L ? 1 : 0) + (B ? 1 : 0) + (R ? 1 : 0)) % 2 == 0,
                    new int[]{1,1,1}
            )
    );
    // -------------------------------------------------------------------------------------------------------
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
