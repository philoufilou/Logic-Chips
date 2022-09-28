package com.ichphilipp.logicgates.allblocks;

import com.ichphilipp.logicgates.utils.GateFrameTypes;
import com.ichphilipp.logicgates.utils.TriFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class ChipFrame extends RedstoneDiodeBlock {
    public static final EnumProperty<GateFrameTypes> TYPE = EnumProperty.create("type", GateFrameTypes.class);
    public static final BooleanProperty LEFT_INPUT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT_INPUT = BooleanProperty.create("right");
    public static final BooleanProperty BOTTOM_INPUT = BooleanProperty.create("bottom");
    private static final HashMap<IItemProvider, GateFrameTypes> __1__ = new HashMap<>();
    private static final HashMap<GateFrameTypes, IItemProvider> __2__ = new HashMap<>();
    private static final HashMap<GateFrameTypes, TriFunction<Boolean,Boolean,Boolean,Boolean>> __3__ = new HashMap<>();
    private static final HashMap<GateFrameTypes, int[]> __4__ = new HashMap<>();
    public static void add(GateFrameTypes gateFrameTypes, IItemProvider itemProvider, TriFunction<Boolean,Boolean,Boolean,Boolean> TRI, int[] ints) {
        __1__.put(itemProvider.asItem(), gateFrameTypes);
        __2__.put(gateFrameTypes, itemProvider.asItem());
        __3__.put(gateFrameTypes, TRI);
        __4__.put(gateFrameTypes, ints);

    }
    public ChipFrame(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, GateFrameTypes.Empty)
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
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {

        Comparable<GateFrameTypes> type = state.getValue(TYPE);
        if (__4__.containsKey(type)){
            int[] aaa = __4__.get(type);

            return side == state.getValue(FACING) ||
                    ( side == state.getValue(FACING).getClockWise() && aaa[2] == 1) ||
                    ( side == state.getValue(FACING).getCounterClockWise() && aaa[0] == 1) ||
                    ( side == state.getValue(FACING).getOpposite() && aaa[1] == 1);
        }
        return false;
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

            Comparable<GateFrameTypes> type = blockState.getValue(TYPE);
            if (__2__.containsKey(type)){
                this.dropChip(world, blockPos, new ItemStack(__2__.get(type)));
            }
            this.updateNeighborsInFront(world, blockPos, blockState);
        }
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
        if (__3__.containsKey(type)){
            TriFunction<Boolean,Boolean,Boolean,Boolean> output = __3__.get(type);
            return output.apply(LEFT, BOTTOM, RIGHT);
        }
        return false;
    }
    public void dropChip(World world, BlockPos blockPos, ItemStack itemStack){
        double d0 = (double)(world.random.nextFloat() * 0.7F) + (double)0.15F;
        double d1 = (double)(world.random.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
        double d2 = (double)(world.random.nextFloat() * 0.7F) + (double)0.15F;
        ItemEntity itementity = new ItemEntity(world, (double)blockPos.getX() + d0, (double)blockPos.getY() + d1, (double)blockPos.getZ() + d2, itemStack);
        itementity.setDefaultPickUpDelay();
        world.addFreshEntity(itementity);
    }
    @Override
    public @NotNull ActionResultType use(BlockState blockState, @NotNull World world, @NotNull BlockPos blockPos, PlayerEntity player, @NotNull Hand Hand, @NotNull BlockRayTraceResult p_225533_6_) {
        Comparable<GateFrameTypes> type = blockState.getValue(TYPE);
        ItemStack hand = player.getItemInHand(Hand);
        Item handitem = hand.getItem();
        /// NOTE: INSERT ITEM ////////////////////////////////////////////////////////////////////////////////
        if ( type ==  GateFrameTypes.Empty && __1__.containsKey(handitem)){
            if (!world.isClientSide) {
                BlockState newBlockstate = blockState.setValue(TYPE, __1__.get(handitem));
                world.setBlock(blockPos,
                        newBlockstate.setValue(POWERED, this.isPowered(newBlockstate, world, blockPos)),
                        3);
                if (!player.abilities.instabuild) {
                    hand.shrink(1);
                }
            }
            return ActionResultType.sidedSuccess(world.isClientSide);
        }
        /// NOTE: DROP ITEM ////////////////////////////////////////////////////////////////////////////////
        else if (type !=  GateFrameTypes.Empty) {
            this.dropChip(world, blockPos, new ItemStack(__2__.get(type)));
            world.setBlock(blockPos,
                    blockState.setValue(TYPE, GateFrameTypes.Empty).setValue(POWERED,false),
                    3);
            return ActionResultType.sidedSuccess(world.isClientSide);
        }
        /// NOTE: DO NOTHING ////////////////////////////////////////////////////////////////////////////////
        else {
            return ActionResultType.PASS;
        }
    }
}
