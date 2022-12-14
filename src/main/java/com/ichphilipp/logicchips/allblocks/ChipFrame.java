package com.ichphilipp.logicchips.allblocks;

import com.ichphilipp.logicchips.utils.GateFrameTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Random;

public class ChipFrame extends RedstoneDiodeBlock {
    public static final EnumProperty<GateFrameTypes> TYPE = EnumProperty.create("type", GateFrameTypes.class);
    public static final BooleanProperty LEFT_INPUT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT_INPUT = BooleanProperty.create("right");
    public static final BooleanProperty BOTTOM_INPUT = BooleanProperty.create("bottom");
    public static final HashMap<Item, GateFrameTypes> __1__ = new HashMap<>();
    public static final HashMap<String, Item> __2__ = new HashMap<>();
    public static void add(GateFrameTypes gateFrameTypes, Item item) {
        __1__.put(item, gateFrameTypes);
        __2__.put(gateFrameTypes.toString(), item);
    }
    public ChipFrame(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, GateFrameTypes.empty)
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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, POWERED, LEFT_INPUT, RIGHT_INPUT, BOTTOM_INPUT);
    }
    @Override
    public boolean canConnectRedstone(BlockState blockState, IBlockReader world, BlockPos pos, @Nullable Direction direction) {
        Comparable<GateFrameTypes> type = blockState.getValue(TYPE);
        int connect = GateFrameTypes.valueOf(type.toString()).canConnectTo();
        return direction == blockState.getValue(FACING) ||
                ( direction == blockState.getValue(FACING).getClockWise() && (connect == 2 || connect == 3) )||
                ( direction == blockState.getValue(FACING).getCounterClockWise() && (connect == 2 || connect == 3) )||
                ( direction == blockState.getValue(FACING).getOpposite() && (connect == 1 || connect == 3) );
    }
    @Override
    protected int getInputSignal(@NotNull World world, @NotNull BlockPos pos, @NotNull BlockState state) {
        if(this.isPowered(state, world, pos)){
            return 15;
        }
        return 0;
    }
    @Override
    public void onRemove(@NotNull BlockState blockState, @NotNull World world, @NotNull BlockPos blockPos, @NotNull BlockState newblockState, boolean isMoving) {
        if (!isMoving && !blockState.is(newblockState.getBlock())) {
            super.onRemove(blockState, world, blockPos, newblockState, false);
            this.dropChip(world, blockPos, blockState);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, world, pos, explosion);
        this.dropChip(world, pos, state);
    }

    public boolean isPowered(@NotNull BlockState blockstate, World world, @NotNull BlockPos blockpos){
        Direction facing = blockstate.getValue(FACING);
        Comparable<GateFrameTypes> type = blockstate.getValue(TYPE);
        boolean RIGHT = getAlternateSignalAt(world, blockpos.relative(facing.getCounterClockWise()), facing.getCounterClockWise()) > 0;
        boolean BOTTOM = getAlternateSignalAt(world, blockpos.relative(facing), facing) > 0;
        boolean LEFT = getAlternateSignalAt(world, blockpos.relative(facing.getClockWise()), facing.getClockWise()) > 0;

        world.setBlockAndUpdate(blockpos,
                blockstate
                        .setValue(LEFT_INPUT, LEFT)
                        .setValue(RIGHT_INPUT, RIGHT)
                        .setValue(BOTTOM_INPUT, BOTTOM)
        );
        if (__1__.containsValue(type)){
            return GateFrameTypes.valueOf(type.toString()).Outputformal().apply(LEFT, BOTTOM, RIGHT);
        }
        return false;
    }
    public void dropChip(World world, BlockPos blockPos, BlockState blockState){
        Comparable<GateFrameTypes> type = blockState.getValue(TYPE);
        if (type !=  GateFrameTypes.empty) {
            if (__2__.containsKey(type.toString())) {
                double d0 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;
                double d1 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.060000002F + 0.6D;
                double d2 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;
                ItemEntity itementity = new ItemEntity(world, (double) blockPos.getX() + d0, (double) blockPos.getY() + d1, (double) blockPos.getZ() + d2, new ItemStack(__2__.get(type.toString())));
                itementity.setDefaultPickUpDelay();
                world.addFreshEntity(itementity);
            }
            this.updateNeighborsInFront(world, blockPos, blockState);
        }
    }
    @Override
    public @NotNull ActionResultType use(BlockState blockState, @NotNull World world, @NotNull BlockPos blockPos, PlayerEntity player, @NotNull Hand Hand, @NotNull BlockRayTraceResult p_225533_6_) {
        Comparable<GateFrameTypes> type = blockState.getValue(TYPE);

        ItemStack hand = player.getItemInHand(Hand);
        Item handitem = hand.getItem();
        boolean instabuild = !player.abilities.instabuild;
        boolean isClientSide = world.isClientSide;

        /// NOTE: INSERT ITEM ////////////////////////////////////////////////////////////////////////////////
        if ( type ==  GateFrameTypes.empty && __1__.containsKey(handitem)){
            if (!isClientSide) {
                BlockState newBlockstate = blockState.setValue(TYPE, __1__.get(handitem));
                world.setBlock(blockPos,
                        newBlockstate.setValue(POWERED, this.isPowered(newBlockstate, world, blockPos)),
                        3);
                if (instabuild) {
                    hand.shrink(1);
                }
                world.playSound(null, blockPos, SoundEvents.ITEM_FRAME_PLACE, SoundCategory.BLOCKS, 1.0F,1.0F);
            }
            return ActionResultType.sidedSuccess(isClientSide);
        }
        /// NOTE: DROP ITEM ////////////////////////////////////////////////////////////////////////////////
        else if (type !=  GateFrameTypes.empty) {
            if (!isClientSide) {
                world.setBlock(blockPos,
                        blockState.setValue(TYPE, GateFrameTypes.empty).setValue(POWERED,false),
                        3);
                if (instabuild) {
                    this.dropChip(world, blockPos, blockState);
                }
                world.playSound(null, blockPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F,1.0F);
            }
            return ActionResultType.sidedSuccess(isClientSide);
        }
        return ActionResultType.PASS;
    }

    @Override
    public void animateTick(BlockState blockState, World level, BlockPos blockPos, Random randomSource) {
        if (blockState.getValue(POWERED)) {
            Direction direction = blockState.getValue(FACING);
            double d0 = (double)blockPos.getX() + 0.5D + (randomSource.nextDouble() - 0.5D) * 0.2D;
            double d1 = (double)blockPos.getY() + 0.4D + (randomSource.nextDouble() - 0.5D) * 0.2D;
            double d2 = (double)blockPos.getZ() + 0.5D + (randomSource.nextDouble() - 0.5D) * 0.2D;
            float f = -5.0F;
            if (randomSource.nextBoolean()) {
                f /= 16.0F;
                d0+=(f * (float)direction.getStepX());
                d2+=(f * (float)direction.getStepZ());
            }
            level.addParticle(RedstoneParticleData.REDSTONE, d0, d1, d2 , 0.0D, 0.0D, 0.0D);
        }
    }
}
