package com.ichphilipp.logicchips.blocks.dynamic;

import com.ichphilipp.logicchips.items.ChipFrameCompatible;
import lombok.val;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChipFrameDynamic extends DiodeBlock implements EntityBlock {

    //whether there's input signal at specified direction
    public static final BooleanProperty LEFT_INPUT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT_INPUT = BooleanProperty.create("right");
    public static final BooleanProperty BOTTOM_INPUT = BooleanProperty.create("bottom");

    @NotNull
    private ItemStack contained = ItemStack.EMPTY;

    public ChipFrameDynamic(Properties properties) {
        super(properties);
        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LEFT_INPUT, false)
                .setValue(RIGHT_INPUT, false)
                .setValue(BOTTOM_INPUT, false)
                .setValue(POWERED, false)
        );
    }

    @Override
    protected int getDelay(@NotNull BlockState state) {
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg) {
        arg.add(FACING, POWERED, LEFT_INPUT, RIGHT_INPUT, BOTTOM_INPUT);
    }

    /**
     * Forge only
     */
    public boolean canConnectRedstone(
        BlockState blockState,
        BlockGetter world,
        BlockPos pos,
        @Nullable Direction side
    ) {
        val item = ((ChipFrameCompatible) contained.getItem());
        val facing = blockState.getValue(FACING);
        return (
            side == facing ||
                (side == facing.getClockWise() && item.acceptRedstoneAtRight()) ||
                (side == facing.getCounterClockWise() && item.acceptRedstoneAtLeft()) ||
                (side == facing.getOpposite() && item.acceptRedstoneAtRear())
        );
    }

    @Override
    protected int getInputSignal(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
        return this.computeOutput(state, world, pos) ? 15 : 0;
    }

    @Override
    public void onRemove(
        @NotNull BlockState blockState,
        @NotNull Level world,
        @NotNull BlockPos blockPos,
        @NotNull BlockState newblockState,
        boolean isMoving
    ) {
        if (!isMoving && !blockState.is(newblockState.getBlock())) {
            super.onRemove(blockState, world, blockPos, newblockState, false);
            this.dropChip(world, blockPos, blockState);
        }
    }

    // public void wasExploded(Level world, BlockPos pos, Explosion explosion) {
    //     // We don't need this to get our chip back when exploded because onRemove() already did this
    //     // Tested on Forge36.2.41
    // }

    public boolean computeOutput(@NotNull BlockState blockstate, Level world, @NotNull BlockPos pos) {
        val facing = blockstate.getValue(FACING);
        val signalRight = 0 != world.getControlInputSignal(
            pos.relative(facing.getCounterClockWise()),
            facing.getCounterClockWise(),
            this.sideInputDiodesOnly()
        );
        val signalBack = 0 != world.getControlInputSignal(pos.relative(facing), facing, this.sideInputDiodesOnly());
        val signalLeft = 0 != world.getControlInputSignal(
            pos.relative(facing.getClockWise()),
            facing.getClockWise(),
            this.sideInputDiodesOnly()
        );

        world.setBlockAndUpdate(
            pos,
            blockstate.setValue(LEFT_INPUT, signalLeft)
                .setValue(RIGHT_INPUT, signalRight)
                .setValue(BOTTOM_INPUT, signalBack)
        );
        val item = (ChipFrameCompatible) contained.getItem();
        return item.logic().apply(signalLeft, signalBack, signalRight);
    }

    public void dropChip(Level world, BlockPos blockPos, BlockState blockState) {
        if (contained.isEmpty()) {
            return;
        }
        Containers.dropItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), contained);
        this.updateNeighborsInFront(world, blockPos, blockState);
    }

    @Override
    public @NotNull InteractionResult use(
        BlockState blockState,
        @NotNull Level world,
        @NotNull BlockPos blockPos,
        Player player,
        @NotNull InteractionHand hand,
        @NotNull BlockHitResult hit
    ) {
        val stack = player.getItemInHand(hand);
        val playerCreative = player.isCreative(); //true if in creative
        val isClientSide = world.isClientSide;

        if (!contained.isEmpty()) { //should drop contained item
            if (!isClientSide) {
                this.dropChip(world, blockPos, blockState);
                contained = ItemStack.EMPTY;
                world.playSound(
                    null,
                    blockPos,
                    SoundEvents.ITEM_FRAME_REMOVE_ITEM,
                    SoundSource.BLOCKS,
                    1.0F,
                    1.0F
                );
            }
            return InteractionResult.sidedSuccess(isClientSide);
        } else if (!stack.isEmpty() && stack.getItem() instanceof ChipFrameCompatible) {
            if (!isClientSide) {
                contained = stack.copyWithCount(1);
                if (!playerCreative) {
                    stack.shrink(1);
                }
                world.playSound(null, blockPos, SoundEvents.ITEM_FRAME_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (!blockState.getValue(POWERED)) {
            return;
        }
        val direction = blockState.getValue(FACING);
        double x = (double) blockPos.getX() + 0.5D + (randomSource.nextDouble() - 0.5D) * 0.2D;
        double y = (double) blockPos.getY() + 0.4D + (randomSource.nextDouble() - 0.5D) * 0.2D;
        double z = (double) blockPos.getZ() + 0.5D + (randomSource.nextDouble() - 0.5D) * 0.2D;
        float scale = -5.0F;
        if (randomSource.nextBoolean()) {
            scale /= 16.0F;
            x += (scale * direction.getStepX());
            z += (scale * direction.getStepZ());
        }
        level.addParticle(DustParticleOptions.REDSTONE, x, y, z, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ContainerChipFrameDynamic(blockPos, blockState);
    }
}
