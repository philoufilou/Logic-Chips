package com.ichphilipp.logicchips.utils;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.blocks.LogicChipsBlock;
import com.ichphilipp.logicchips.items.LogicChipsItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RegistryMgr {

    public static final Map<String, LogicChipsItem> ITEMS = new HashMap<>();
    public static final Map<String, LogicChipsBlock> BLOCKS = new HashMap<>();

    public static final DeferredRegister<Item> ITEM_REG = DeferredRegister.create(
        LogicChips.MOD_ID,
        Registry.ITEM_REGISTRY
    );
    public static final DeferredRegister<Block> BLOCK_REG = DeferredRegister.create(
        LogicChips.MOD_ID,
        Registry.BLOCK_REGISTRY
    );

    public static <T extends Item> RegistrySupplier<T> registerItem(String name, Supplier<T> item) {
        return ITEM_REG.register(name, item);
    }

    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        return BLOCK_REG.register(name, block);
    }

    public static <T extends Block> RegistrySupplier<BlockItem> registerBlockItem(String name, RegistrySupplier<T> block) {
        return ITEM_REG.register(name, () -> new BlockItem(block.get(), LogicChips.DEFAULT_BLOCK_PROP));
    }
}
