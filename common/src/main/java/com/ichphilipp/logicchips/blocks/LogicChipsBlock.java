package com.ichphilipp.logicchips.blocks;

import com.ichphilipp.logicchips.RegistryMgr;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public enum LogicChipsBlock implements Supplier<Block> {
    GATE_FRAME(new ChipFrame(BlockBehaviour.Properties.copy(Blocks.REPEATER)));

    private final String key;
    private final Supplier<Block> block;

    @Override
    public Block get() {
        return this.block.get();
    }

    LogicChipsBlock(Block template) {
        this.key = this.name().toLowerCase();
        this.block = RegistryMgr.registerBlock(this.key, () -> template);
    }

    public static void init() {
        RegistryMgr.BLOCK_REG.register();
        for (LogicChipsBlock block : LogicChipsBlock.values()) {
            RegistryMgr.BLOCKS.put(block.key, block);
        }
    }
}
