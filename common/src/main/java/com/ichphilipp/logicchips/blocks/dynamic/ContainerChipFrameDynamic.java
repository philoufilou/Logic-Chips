package com.ichphilipp.logicchips.blocks.dynamic;

import com.ichphilipp.logicchips.utils.ContainerSingle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * @author ZZZank
 */
public class ContainerChipFrameDynamic extends BlockEntity implements ContainerSingle {

    @NotNull
    private ItemStack contained = ItemStack.EMPTY;

    public ContainerChipFrameDynamic(
        BlockPos blockPos,
        BlockState blockState
    ) {
        super(null, blockPos, blockState); //todo
        ChestBlockEntity b;
    }

    @Override
    public @NotNull ItemStack getContained() {
        return contained;
    }

    @Override
    public void setContained(@NotNull ItemStack stack) {
        contained = stack;
    }
}
