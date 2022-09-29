package com.ichphilipp.logicchips.allitems;

import com.ichphilipp.logicchips.utils.ChipConfig;
import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.utils.GateFrameTypes;
import com.ichphilipp.logicchips.utils.ITEM_GROUP;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllItems {
    public static final int MAX_STACK_SIZE_CHIPS = 16;//ChipConfig.max_stack_size_chips.get();
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LogicChips.MODID);
    // -------------------------------------------------------------------------------------------------------
    public static final RegistryObject<Item> CHIP = ITEMS.register("chip",
            () -> new Item(new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP)));
    // -------------------------------------------------------------------------------------------------------
    public static final RegistryObject<Item> NOT_GATE = ITEMS.register("not_gate",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.not
            )
    );
    public static final RegistryObject<Item> AND_GATE = ITEMS.register("and_gate",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.and
            )
    );
    public static final RegistryObject<Item> NAND_GATE = ITEMS.register("nand_gate",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.nand
            )
    );
    public static final RegistryObject<Item> OR_GATE = ITEMS.register("or_gate",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.or
            )
    );
    public static final RegistryObject<Item> NOR_GATE = ITEMS.register("nor_gate",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.nor
            )
    );
    public static final RegistryObject<Item> XOR_GATE = ITEMS.register("xor_gate",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.xor
            )
    );
    public static final RegistryObject<Item> XNOR_GATE = ITEMS.register("xnor_gate",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.xnor
            )
    );
    // -------------------------------------------------------------------------------------------------------
    public static final RegistryObject<Item> AND_GATE_3 = ITEMS.register("and_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.and_3
            )
    );
    public static final RegistryObject<Item> NAND_GATE_3 = ITEMS.register("nand_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.nand_3
            )
    );
    public static final RegistryObject<Item> OR_GATE_3 = ITEMS.register("or_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.or_3
            )
    );
    public static final RegistryObject<Item> NOR_GATE_3 = ITEMS.register("nor_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.nor_3
            )
    );
    public static final RegistryObject<Item> XOR_GATE_3 = ITEMS.register("xor_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.xor_3
            )
    );
    public static final RegistryObject<Item> XNOR_GATE_3 = ITEMS.register("xnor_gate_3",
            () -> new Chip(
                    new Item.Properties().tab(ITEM_GROUP.ITEM_GROUP).stacksTo(MAX_STACK_SIZE_CHIPS),
                    GateFrameTypes.xnor_3
            )
    );
    // -------------------------------------------------------------------------------------------------------
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
