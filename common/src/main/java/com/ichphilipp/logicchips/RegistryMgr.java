package com.ichphilipp.logicchips;

import com.ichphilipp.logicchips.items.LogicChipItem;
import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RegistryMgr {

    public static final Map<String, LogicChipItem> ITEMS = new HashMap<>();
    public static final DeferredRegister<Item> ITEM_REG = DeferredRegister.create(
        LogicChips.MOD_ID,
        Registry.ITEM_REGISTRY
    );
    public static final DeferredRegister<Block> BLOCK_REG = DeferredRegister.create(
        LogicChips.MOD_ID,
        Registry.BLOCK_REGISTRY
    );

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        Supplier<T> toReturn = BLOCK_REG.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block) {
        ITEM_REG.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(LogicChips.ITEM_GROUP)));
    }
}
