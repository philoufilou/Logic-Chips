package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.utils.RegistryMgr;
import com.ichphilipp.logicchips.api.TriBoolLogic;
import com.ichphilipp.logicchips.utils.ChipType;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Supplier;

public enum LogicChipsItem implements Supplier<Item> {
    //chip base
    CHIP(),
    //dual-input gate
    NOT_GATE(ChipType.not),
    AND_GATE(ChipType.and),
    NAND_GATE(ChipType.nand),
    OR_GATE(ChipType.or),
    NOR_GATE(ChipType.nor),
    XOR_GATE(ChipType.xor),
    XNOR_GATE(ChipType.xnor),
    //tri-input gate
    AND_GATE_3(ChipType.and_3),
    NAND_GATE_3(ChipType.nand_3),
    OR_GATE_3(ChipType.or_3),
    NOR_GATE_3(ChipType.nor_3),
    XOR_GATE_3(ChipType.xor_3),
    XNOR_GATE_3(ChipType.xnor_3);

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
        this.key = this.name().toLowerCase(Locale.ENGLISH); // OR_GATE_3 -> or_gate_3
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
