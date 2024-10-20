package com.ichphilipp.logicchips.utils;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.blocks.LogicChipsBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RegistryMgr {

    public static final Map<String, LogicChipsBlock> BLOCKS = new HashMap<>();

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
        LogicChips.MOD_ID, Registries.CREATIVE_MODE_TAB
    );
    public static final DeferredRegister<Item> ITEM_REG = DeferredRegister.create(
        LogicChips.MOD_ID, Registries.ITEM
    );
    public static final DeferredRegister<Block> BLOCK_REG = DeferredRegister.create(
        LogicChips.MOD_ID, Registries.BLOCK
    );

    public static <T extends Item> RegistrySupplier<T> registerItem(String name, Supplier<T> item) {
        return ITEM_REG.register(name, item);
    }

    public static RegistrySupplier<Item> registerItem(String name) {
        return registerItem(name, () -> new Item(LogicChips.DEFAULT_ITEM_PROP));
    }

    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        return BLOCK_REG.register(name, block);
    }

    public static <T extends Block> RegistrySupplier<BlockItem> registerBlockItem(String name, RegistrySupplier<T> block) {
        return ITEM_REG.register(name, () -> new BlockItem(block.get(), LogicChips.DEFAULT_ITEM_PROP));
    }

    public static void init() {
        TABS.register();
        ITEM_REG.register();
        BLOCK_REG.register();
    }
}
