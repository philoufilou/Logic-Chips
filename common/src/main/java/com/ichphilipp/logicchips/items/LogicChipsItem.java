package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.RegistryMgr;
import com.ichphilipp.logicchips.api.TriBoolLogic;
import com.ichphilipp.logicchips.utils.GateFrameTypes;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public enum LogicChipsItem implements Supplier<Item> {
    //chip base
    CHIP(),
    //dual-input gate
    NOT_GATE(GateFrameTypes.NOT),
    AND_GATE(GateFrameTypes.AND),
    NAND_GATE(GateFrameTypes.NAND),
    OR_GATE(GateFrameTypes.OR),
    NOR_GATE(GateFrameTypes.NOR),
    XOR_GATE(GateFrameTypes.XOR),
    XNOR_GATE(GateFrameTypes.XNOR),
    //tri-input gate
    AND_GATE_3(GateFrameTypes.AND_3),
    NAND_GATE_3(GateFrameTypes.NAND_3),
    OR_GATE_3(GateFrameTypes.OR_3),
    NOR_GATE_3(GateFrameTypes.NOR_3),
    XOR_GATE_3(GateFrameTypes.XOR_3),
    XNOR_GATE_3(GateFrameTypes.XNOR_3);

    @Override
    public Item get() {
        return this.item.get();
    }

    @Nullable
    public TriBoolLogic logic() {
        return logic;
    }

    private final String key;
    private final TriBoolLogic logic;
    private final RegistrySupplier<Item> item;

    /**
     * register a chip item
     * @param type determines the logic and corresponding blockstate name of such chip
     */
    LogicChipsItem(GateFrameTypes type) {
        this.key = this.name().toLowerCase(); // OR_GATE_3 -> or_gate_3
        this.logic = type.logic();
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
