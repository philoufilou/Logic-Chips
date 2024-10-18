package com.ichphilipp.logicchips.recipe;

import lombok.AllArgsConstructor;
import lombok.val;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.IntTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * @author ZZZank
 */
@AllArgsConstructor
public class DynamicChipRecipe implements CraftingRecipe {

    public static final int MARKER_COUNT = 2 * 2 * 2;
    public static final int[] BIT_WISE_PATTERN;

    static {
        BIT_WISE_PATTERN = new int[MARKER_COUNT];
        var bit = 1;
        for (int i = 0; i < MARKER_COUNT; i++) {
            BIT_WISE_PATTERN[i] = bit;
            bit <<= 1;
        }
    }

    public final ResourceLocation id;
    public final Ingredient base;
    public final Ingredient marker;
    public final ItemStack result;

    @Override
    @NotNull
    public CraftingBookCategory category() {
        return CraftingBookCategory.REDSTONE;
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        return base.test(container.getItem(3 * 3 - 1));
    }

    @Override
    @NotNull
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        var bits = 0;
        for (int i = 0; i < MARKER_COUNT; i++) {
            val m = container.getItem(i);
            if (marker.test(m)) {
                bits |= BIT_WISE_PATTERN[i];
            }
        }
        val result = this.result.copy();
        result.addTagElement("logicchips_bits", IntTag.valueOf(bits));
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i * j > MARKER_COUNT;
    }

    @Override
    @NotNull
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @NotNull
    public RecipeSerializer<?> getSerializer() {
        return DynamicChipRecipeSerializer.INSTANCE;
    }
}
