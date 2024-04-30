package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.utils.RegistryMgr;
import com.ichphilipp.logicchips.api.TriBoolLogic;
import com.ichphilipp.logicchips.utils.ChipType;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public enum LogicChipsItem implements Supplier<Item> {
    //chip base
    CHIP(),
    //dual-input gate
    NOT_GATE(ChipType.NOT),
    AND_GATE(ChipType.AND),
    NAND_GATE(ChipType.NAND),
    OR_GATE(ChipType.OR),
    NOR_GATE(ChipType.NOR),
    XOR_GATE(ChipType.XOR),
    XNOR_GATE(ChipType.XNOR),
    //tri-input gate
    AND_GATE_3(ChipType.AND_3),
    NAND_GATE_3(ChipType.NAND_3),
    OR_GATE_3(ChipType.OR_3),
    NOR_GATE_3(ChipType.NOR_3),
    XOR_GATE_3(ChipType.XOR_3),
    XNOR_GATE_3(ChipType.XNOR_3);

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
    LogicChipsItem(ChipType type) {
        this.key = this.name().toLowerCase(); // OR_GATE_3 -> or_gate_3
        this.logic = type.logic();
        this.item = RegistryMgr.registerItem(this.key, () -> new Chip(LogicChips.DEFAULT_CHIP_PROP, type));
    }

    /**
     * register an item with its name specified by the name of enum constant
     */
    LogicChipsItem() {
        this.key = this.name().toLowerCase(); // CHIP -> chip
        this.logic = null;
        this.item = RegistryMgr.registerItem(this.key);
    }

    public static void init() {
        RegistryMgr.ITEM_REG.register();
        for (LogicChipsItem item : LogicChipsItem.values()) {
            RegistryMgr.ITEMS.put(item.key, item);
        }
    }
}
