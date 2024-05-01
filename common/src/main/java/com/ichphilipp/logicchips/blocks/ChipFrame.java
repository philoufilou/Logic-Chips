package com.ichphilipp.logicchips.blocks;

import com.ichphilipp.logicchips.utils.ChipType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import lombok.val;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChipFrame extends DiodeBlock {

    public static final EnumProperty<ChipType> TYPE = EnumProperty.create("type", ChipType.class);
    public static final BooleanProperty LEFT_INPUT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT_INPUT = BooleanProperty.create("right");
    public static final BooleanProperty BOTTOM_INPUT = BooleanProperty.create("bottom");
    public static final Map<Item, ChipType> chip2logic = new HashMap<>();
    public static final Map<String, Item> name2chip = new HashMap<>();

    public ChipFrame(Properties properties) {
        super(properties);
        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, ChipType.empty)
                .setValue(LEFT_INPUT, false)
                .setValue(RIGHT_INPUT, false)
                .setValue(BOTTOM_INPUT, false)
                .setValue(POWERED, false)
        );
    }

    public static void add(ChipType chipType, Item item) {
        chip2logic.put(item, chipType);
        name2chip.put(chipType.toString(), item);
    }

    @Override
    protected int getDelay(@NotNull BlockState state) {
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg) {
        arg.add(FACING, TYPE, POWERED, LEFT_INPUT, RIGHT_INPUT, BOTTOM_INPUT);
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
        val type = blockState.getValue(TYPE);
        val connect = ChipType.valueOf(type.toString()).canConnectTo();
        val facing = blockState.getValue(FACING);
        return (
            side == facing ||
                (side == facing.getClockWise() && (connect == 2 || connect == 3)) ||
                (side == facing.getCounterClockWise() && (connect == 2 || connect == 3)) ||
                (side == facing.getOpposite() && (connect == 1 || connect == 3))
        );
    }

    @Override
    protected int getInputSignal(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
        return this.isPowered(state, world, pos) ? 15 : 0;
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

    public boolean isPowered(@NotNull BlockState blockstate, Level world, @NotNull BlockPos blockpos) {
        val facing = blockstate.getValue(FACING);
        val type = blockstate.getValue(TYPE);
        val RIGHT = getAlternateSignalAt(world,
            blockpos.relative(facing.getCounterClockWise()),
            facing.getCounterClockWise()
        ) > 0;
        val BACK = getAlternateSignalAt(world, blockpos.relative(facing), facing) > 0;
        val LEFT = getAlternateSignalAt(world, blockpos.relative(facing.getClockWise()), facing.getClockWise()) > 0;

        world.setBlockAndUpdate(
            blockpos,
            blockstate.setValue(LEFT_INPUT, LEFT).setValue(RIGHT_INPUT, RIGHT).setValue(BOTTOM_INPUT, BACK)
        );
        if (chip2logic.containsValue(type)) {
            return ChipType.valueOf(type.toString()).apply(LEFT, BACK, RIGHT);
        }
        return false;
    }

    public void dropChip(Level world, BlockPos blockPos, BlockState blockState) {
        val type = blockState.getValue(TYPE);
        if (type == ChipType.empty) {
            return;
        }
        if (name2chip.containsKey(type.toString())) {
            double d0 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;
            double d1 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.060000002F + 0.6D;
            double d2 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;
            ItemEntity itementity = new ItemEntity(
                world,
                (double) blockPos.getX() + d0,
                (double) blockPos.getY() + d1,
                (double) blockPos.getZ() + d2,
                new ItemStack(name2chip.get(type.toString()))
            );
            itementity.setDefaultPickUpDelay();
            world.addFreshEntity(itementity);
        }
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
        val type = blockState.getValue(TYPE);
        val stack = player.getItemInHand(hand);
        val handitem = stack.getItem();
        val instabuild = !player.getAbilities().instabuild;
        val isClientSide = world.isClientSide;

        /// NOTE: INSERT ITEM ////////////////////////////////////////////////////////////////////////////////
        if (type == ChipType.empty && chip2logic.containsKey(handitem)) {
            if (!isClientSide) {
                BlockState newBlockstate = blockState.setValue(TYPE, chip2logic.get(handitem));
                world.setBlock(
                    blockPos,
                    newBlockstate.setValue(POWERED, this.isPowered(newBlockstate, world, blockPos)),
                    3
                );
                if (instabuild) {
                    stack.shrink(1);
                }
                world.playSound(null, blockPos, SoundEvents.ITEM_FRAME_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(isClientSide);
        }
        /// NOTE: DROP ITEM ////////////////////////////////////////////////////////////////////////////////
        else if (type != ChipType.empty) {
            if (!isClientSide) {
                world.setBlock(
                    blockPos,
                    blockState.setValue(TYPE, ChipType.empty).setValue(POWERED, false),
                    3
                );
                if (instabuild) {
                    this.dropChip(world, blockPos, blockState);
                }
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
        }
        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random randomSource) {
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
}
