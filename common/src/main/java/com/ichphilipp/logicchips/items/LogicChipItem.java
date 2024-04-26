package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.RegistryMgr;
import com.ichphilipp.logicchips.api.TriInputGate;
import com.ichphilipp.logicchips.utils.GateFrameTypes;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public enum LogicChipItem implements Supplier<Item> {
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
    public TriInputGate logic() {
        return logic;
    }

    private final String key;
    private final TriInputGate logic;
    private final Supplier<Item> item;

    LogicChipItem(GateFrameTypes type) {
        this.key = this.name().toLowerCase(); // OR_GATE_3 -> or_gate_3
        this.logic = type.Outputformal();
        this.item = RegistryMgr.ITEM_REG.register(this.key, () -> new Chip(Chip.DEFAULT_PROP, type));
    }

    LogicChipItem() {
        this.key = this.name().toLowerCase(); // CHIP -> chip
        this.logic = null;
        this.item = RegistryMgr.ITEM_REG.register(this.key, () -> new Item(Chip.DEFAULT_PROP));
    }

    public static void init() {
        RegistryMgr.ITEM_REG.register();
        for (LogicChipItem item : LogicChipItem.values()) {
            RegistryMgr.ITEMS.put(item.key, item);
        }
    }
}
