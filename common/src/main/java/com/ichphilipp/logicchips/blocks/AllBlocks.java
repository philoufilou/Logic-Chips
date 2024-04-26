package com.ichphilipp.logicchips.blocks;

import java.util.function.Supplier;

import com.ichphilipp.logicchips.RegistryMgr;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AllBlocks {

    public static final Supplier<Block> GATE_FRAME;

    static {
        GATE_FRAME = RegistryMgr.registerBlock("gate_frame",
            () -> new ChipFrame(BlockBehaviour.Properties.copy(Blocks.REPEATER))
        );
    }

    public static void init() {
        RegistryMgr.BLOCK_REG.register();
    }
}
