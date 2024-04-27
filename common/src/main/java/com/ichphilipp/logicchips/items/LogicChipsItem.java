package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.RegistryMgr;
import com.ichphilipp.logicchips.api.TriInputLogic;
import com.ichphilipp.logicchips.utils.GateFrameTypes;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public enum LogicChipsItem implements Supplier<Item> {
    //chip base
    CHIP(),
    //dual-input gate
    NOT_GATE(GateFrameTypes.not),
    AND_GATE(GateFrameTypes.and),
    NAND_GATE(GateFrameTypes.nand),
    OR_GATE(GateFrameTypes.or),
    NOR_GATE(GateFrameTypes.nor),
    XOR_GATE(GateFrameTypes.xor),
    XNOR_GATE(GateFrameTypes.xnor),
    //tri-input gate
    AND_GATE_3(GateFrameTypes.and_3),
    NAND_GATE_3(GateFrameTypes.nand_3),
    OR_GATE_3(GateFrameTypes.or_3),
    NOR_GATE_3(GateFrameTypes.nor_3),
    XOR_GATE_3(GateFrameTypes.xor_3),
    XNOR_GATE_3(GateFrameTypes.xnor_3);

    @Override
    public Item get() {
        return this.item.get();
    }

    @Nullable
    public TriInputLogic logic() {
        return logic;
    }

    private final String key;
    private final TriInputLogic logic;
    private final Supplier<Item> item;

    /**
     * register a chip item
     * @param type determines the logic and corresponding blockstate name of such chip
     */
    LogicChipsItem(GateFrameTypes type) {
        this.key = this.name().toLowerCase(); // OR_GATE_3 -> or_gate_3
        this.logic = type.Outputformal();
        this.item = RegistryMgr.ITEM_REG.register(this.key, () -> new Chip(LogicChips.DEFAULT_ITEM_PROP, type));
    }

    /**
     * register an item with its name specified by the name of enum constant
     */
    LogicChipsItem() {
        this.key = this.name().toLowerCase(); // CHIP -> chip
        this.logic = null;
        this.item = RegistryMgr.ITEM_REG.register(this.key, () -> new Item(LogicChips.DEFAULT_ITEM_PROP));
    }

    public static void init() {
        RegistryMgr.ITEM_REG.register();
        for (LogicChipsItem item : LogicChipsItem.values()) {
            RegistryMgr.ITEMS.put(item.key, item);
        }
    }
}
