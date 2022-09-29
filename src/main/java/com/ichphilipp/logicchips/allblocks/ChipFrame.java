package com.ichphilipp.logicchips.allblocks;

import com.ichphilipp.logicchips.utils.GateFrameTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ChipFrame extends DiodeBlock{
    public static final EnumProperty<GateFrameTypes> TYPE = EnumProperty.create("type", GateFrameTypes.class);
    public static final BooleanProperty LEFT_INPUT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT_INPUT = BooleanProperty.create("right");
    public static final BooleanProperty BOTTOM_INPUT = BooleanProperty.create("bottom");
    private static final HashMap<Item, GateFrameTypes> __1__ = new HashMap<>();
    private static final HashMap<String, Item> __2__ = new HashMap<>();
    public static void add(GateFrameTypes gateFrameTypes, Item chip) {
        __1__.put(chip, gateFrameTypes);
        __2__.put(gateFrameTypes.toString(), chip);
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
    protected int getDelay(@NotNull BlockState blockState) {
        return 0;
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, POWERED, LEFT_INPUT, RIGHT_INPUT, BOTTOM_INPUT);
    }
    /*
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
    */
    @Override
    protected int getInputSignal(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
        if(this.isPowered(state, level, pos)){
            return 15;
        }
        return 0;
    }
    @Override
    public void onRemove(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState newblockState, boolean isMoving) {
        if (!isMoving && !blockState.is(newblockState.getBlock())) {
            super.onRemove(blockState, level, blockPos, newblockState, false);

            Comparable<GateFrameTypes> type = blockState.getValue(TYPE);
            if (__2__.containsKey(type.toString())){
                this.dropChip(level, blockPos, new ItemStack(__2__.get(type.toString())));
            }
            this.updateNeighborsInFront(level, blockPos, blockState);
        }
    }
    public boolean isPowered(@NotNull BlockState blockstate, Level level, @NotNull BlockPos blockpos){
        Direction facing = blockstate.getValue(FACING);
        Comparable<GateFrameTypes> type = blockstate.getValue(TYPE);// empty
        boolean RIGHT = getAlternateSignalAt(level, blockpos.relative(facing.getCounterClockWise()), facing.getCounterClockWise()) > 0;
        boolean BOTTOM = getAlternateSignalAt(level, blockpos.relative(facing), facing) > 0;
        boolean LEFT = getAlternateSignalAt(level, blockpos.relative(facing.getClockWise()), facing.getClockWise()) > 0;

        level.setBlockAndUpdate(blockpos,
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
    public void dropChip(Level level, BlockPos blockPos, ItemStack itemStack){
        double d0 = (double)(level.random.nextFloat() * 0.7F) + (double)0.15F;
        double d1 = (double)(level.random.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
        double d2 = (double)(level.random.nextFloat() * 0.7F) + (double)0.15F;
        ItemEntity itementity = new ItemEntity(level, (double)blockPos.getX() + d0, (double)blockPos.getY() + d1, (double)blockPos.getZ() + d2, itemStack);
        itementity.setDefaultPickUpDelay();
        level.addFreshEntity(itementity);
    }
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        Comparable<GateFrameTypes> type = blockState.getValue(TYPE);
        ItemStack hand = player.getItemInHand(interactionHand);
        Item handitem = hand.getItem();
        /// NOTE: INSERT ITEM ////////////////////////////////////////////////////////////////////////////////
        if ( type ==  GateFrameTypes.empty && __1__.containsKey(handitem)){

            if (!level.isClientSide) {
                BlockState newBlockstate = blockState.setValue(TYPE, __1__.get(handitem));
                level.setBlock(blockPos,
                        newBlockstate.setValue(POWERED, this.isPowered(newBlockstate, level, blockPos)),
                        3);
                if (!player.getAbilities().instabuild) {
                    hand.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        /// NOTE: DROP ITEM ////////////////////////////////////////////////////////////////////////////////
        else if (type !=  GateFrameTypes.empty) {
            level.setBlock(blockPos,
                    blockState.setValue(TYPE, GateFrameTypes.empty).setValue(POWERED,false),
                    3);
            if (!player.getAbilities().instabuild) {
                this.dropChip(level, blockPos, new ItemStack(__2__.get(type.toString())));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }
    /*
    public BlockState getStateForPlacement(BlockPlaceContext p_55803_) {
        BlockState blockstate = super.getStateForPlacement(p_55803_);
        return blockstate.setValue(LOCKED, Boolean.valueOf(this.isLocked(p_55803_.getLevel(), p_55803_.getClickedPos(), blockstate)));
    }
    public BlockState updateShape(BlockState p_55821_, Direction p_55822_, BlockState p_55823_, LevelAccessor p_55824_, BlockPos p_55825_, BlockPos p_55826_) {
        return !p_55824_.isClientSide() && p_55822_.getAxis() != p_55821_.getValue(FACING).getAxis() ? p_55821_.setValue(LOCKED, Boolean.valueOf(this.isLocked(p_55824_, p_55825_, p_55821_))) : super.updateShape(p_55821_, p_55822_, p_55823_, p_55824_, p_55825_, p_55826_);
    }
    protected boolean isAlternateInput(@NotNull BlockState blockState) {
        return isDiode(blockState);
    }*/
    public void animateTick(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
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

            level.addParticle(DustParticleOptions.REDSTONE, d0, d1, d2 , 0.0D, 0.0D, 0.0D);
        }
    }

}
