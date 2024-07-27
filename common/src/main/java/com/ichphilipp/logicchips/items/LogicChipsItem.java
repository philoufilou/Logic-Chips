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
    chip(),
    //dual-input gate
    not_gate(ChipType.not),
    and_gate(ChipType.and),
    nand_gate(ChipType.nand),
    or_gate(ChipType.or),
    nor_gate(ChipType.nor),
    xor_gate(ChipType.xor),
    xnor_gate(ChipType.xnor),
    //tri-input gate
    and_gate_3(ChipType.and_3),
    nand_gate_3(ChipType.nand_3),
    or_gate_3(ChipType.or_3),
    nor_gate_3(ChipType.nor_3),
    xor_gate_3(ChipType.xor_3),
    xnor_gate_3(ChipType.xnor_3);

    @Override
    public Item get() {
        return this.item.get();
    }

    @Nullable
    public TriBoolLogic logic() {
        return logic;
    }

    private final TriBoolLogic logic;
    private final RegistrySupplier<Item> item;

    /**
     * register a chip item
     * @param type determines the logic and corresponding blockstate name of such chip
     */
    LogicChipsItem(ChipType type) {
        this.logic = type.logic();
        this.item = RegistryMgr.registerItem(this.name(), () -> new Chip(LogicChips.DEFAULT_CHIP_PROP, type));
    }

    /**
     * register an item with its name specified by the name of enum constant
     */
    LogicChipsItem() {
        this.logic = null;
        this.item = RegistryMgr.registerItem(this.name());
    }

    public static void init() {
        for (LogicChipsItem item : LogicChipsItem.values()) {
            RegistryMgr.ITEMS.put(item.name(), item);
        }
    }
}
