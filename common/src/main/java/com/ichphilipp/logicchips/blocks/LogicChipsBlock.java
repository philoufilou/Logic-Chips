package com.ichphilipp.logicchips.blocks;

import com.ichphilipp.logicchips.utils.RegistryMgr;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public enum LogicChipsBlock implements Supplier<Block> {
    GATE_FRAME(() -> new ChipFrame(BlockBehaviour.Properties.copy(Blocks.REPEATER)));

    private final String key;
    private final RegistrySupplier<Block> block;
    private final RegistrySupplier<BlockItem> item;

    @Override
    public Block get() {
        return this.block.get();
    }

    public RegistrySupplier<BlockItem> item() {
        return this.item;
    }

    LogicChipsBlock(Supplier<Block> template) {
        this.key = this.name().toLowerCase();
        this.block = RegistryMgr.registerBlock(this.key, template);
        this.item = RegistryMgr.registerBlockItem(this.key, this.block);
    }

    public static void init() {
        for (LogicChipsBlock block : LogicChipsBlock.values()) {
            RegistryMgr.BLOCKS.put(block.key, block);
        }
    }
}
