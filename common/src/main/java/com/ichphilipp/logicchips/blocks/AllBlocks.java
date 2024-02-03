package com.ichphilipp.logicchips.blocks;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.items.AllItems;
import java.util.function.Supplier;
import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AllBlocks {

    public static final DeferredRegister<Block> BLOCK_REG = DeferredRegister.create(
        LogicChips.MOD_ID,
        net.minecraft.core.Registry.BLOCK_REGISTRY
    );
    // -------------------------------------------------------------------------------------------------------
    public static final Supplier<Block> GATE_FRAME = registerBlock(
        "gate_frame",
        () -> new ChipFrame(BlockBehaviour.Properties.copy(Blocks.REPEATER))
    );

    // -------------------------------------------------------------------------------------------------------
    private static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        Supplier<T> toReturn = BLOCK_REG.register(new ResourceLocation(LogicChips.MOD_ID, name), block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block) {
        AllItems.ITEM_REG.register(
            new ResourceLocation(LogicChips.MOD_ID, name),
            () -> new BlockItem(block.get(), new Item.Properties().tab(LogicChips.ITEM_GROUP))
        );
    }

    // -------------------------------------------------------------------------------------------------------
    public static void init() {
        BLOCK_REG.register();
    }
}
