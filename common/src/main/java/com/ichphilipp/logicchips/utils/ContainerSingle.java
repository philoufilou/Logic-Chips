package com.ichphilipp.logicchips.utils;

import lombok.val;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @author ZZZank
 */
public interface ContainerSingle extends Container {
    @Override
    default int getContainerSize() {
        return 1;
    }

    @NotNull
    ItemStack getContained();

    void setContained(@NotNull ItemStack stack);

    @Override
    default boolean isEmpty() {
        return getContained().isEmpty();
    }

    @Override
    @NotNull
    default ItemStack getItem(int i) {
        return i ==0 ? getContained() : ItemStack.EMPTY;
    }

    @Override
    @NotNull
    default ItemStack removeItem(int i, int j) {
        return i == 0 ? getContained().split(j) : ItemStack.EMPTY;
    }

    @Override
    @NotNull
    default ItemStack removeItemNoUpdate(int i) {
        val contained = getContained();
        setContained(ItemStack.EMPTY);
        return contained;
    }

    @Override
    default void setItem(int i, ItemStack itemStack) {
        if (i == 0) {
            setContained(itemStack);
        }
    }

    @Override
    default void setChanged() {}

    @Override
    default boolean stillValid(Player player) {
        return true;
    }

    @Override
    default void clearContent() {
        setContained(ItemStack.EMPTY);
    }

    @Override
    default int countItem(Item item) {
        val stack = getContained();
        return stack.getItem().equals(item) ? stack.getCount() : 0;
    }

    @Override
    default boolean hasAnyOf(Set<Item> set) {
        return !getContained().isEmpty() && set.contains(getContained().getItem());
    }

    @Override
    default boolean hasAnyMatching(Predicate<ItemStack> predicate) {
        return predicate.test(getContained());
    }
}
