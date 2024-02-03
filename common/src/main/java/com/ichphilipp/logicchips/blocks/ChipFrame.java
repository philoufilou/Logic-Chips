package com.ichphilipp.logicchips.blocks;

import com.ichphilipp.logicchips.utils.GateFrameTypes;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
// import net.minecraft.block.Block;
// import net.minecraft.block.BlockState;
// import net.minecraft.block.RedstoneDiodeBlock;
// import net.minecraft.entity.item.ItemEntity;
// import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Random;

public class ChipFrame extends DiodeBlock {
    public static final EnumProperty<GateFrameTypes> TYPE = EnumProperty.create("type", (Class<GateFrameTypes>) GateFrameTypes.class);
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg) {
        arg.add(FACING, TYPE, POWERED, LEFT_INPUT, RIGHT_INPUT, BOTTOM_INPUT);
    }

    // TODO: @Override at canConnectRedstone()
    public boolean canConnectRedstone(BlockState blockState, BlockGetter world, BlockPos pos, @Nullable Direction direction) {
        Comparable<GateFrameTypes> type = blockState.getValue(TYPE);
        int connect = GateFrameTypes.valueOf(type.toString()).canConnectTo();
        return direction == blockState.getValue(FACING) ||
                ( direction == blockState.getValue(FACING).getClockWise() && (connect == 2 || connect == 3) )||
                ( direction == blockState.getValue(FACING).getCounterClockWise() && (connect == 2 || connect == 3) )||
                ( direction == blockState.getValue(FACING).getOpposite() && (connect == 1 || connect == 3) );
    }
    @Override
    protected int getInputSignal(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
        if(this.isPowered(state, world, pos)){
            return 15;
        }
        return 0;
    }
    @Override
    public void onRemove(@NotNull BlockState blockState, @NotNull Level world, @NotNull BlockPos blockPos, @NotNull BlockState newblockState, boolean isMoving) {
        if (!isMoving && !blockState.is(newblockState.getBlock())) {
            super.onRemove(blockState, world, blockPos, newblockState, false);
            this.dropChip(world, blockPos, blockState);
        }
    }

    // @Override
    // public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
    //     super.onBlockExploded(state, world, pos, explosion);
    //     this.dropChip(world, pos, state);
    // }
    @Override
    public void wasExploded(Level world, BlockPos pos, Explosion explosion) {
        super.wasExploded(world, pos, explosion);
        this.dropChip(world, pos, world.getBlockState(pos));
    }

    public boolean isPowered(@NotNull BlockState blockstate, Level world, @NotNull BlockPos blockpos){
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
    public void dropChip(Level world, BlockPos blockPos, BlockState blockState){
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
    public @NotNull InteractionResult use(BlockState blockState, @NotNull Level world, @NotNull BlockPos blockPos, Player player, @NotNull InteractionHand Hand, @NotNull BlockHitResult p_225533_6_) {
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
                world.playSound(null, blockPos, SoundEvents.ITEM_FRAME_PLACE, SoundSource.BLOCKS, 1.0F,1.0F);
            }
            return InteractionResult.sidedSuccess(isClientSide);
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
                world.playSound(null, blockPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F,1.0F);
            }
            return InteractionResult.sidedSuccess(isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random randomSource) {
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
