package com.ichphilipp.logicchips.recipe;

import com.google.gson.JsonObject;
import com.ichphilipp.logicchips.LogicChips;
import lombok.val;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

/**
 * @author ZZZank
 */
public class DynamicChipRecipeSerializer implements RecipeSerializer<DynamicChipRecipe> {

    public static final DynamicChipRecipeSerializer INSTANCE = RecipeSerializer.register(
        new ResourceLocation(LogicChips.MOD_ID, "dynamic_chip").toString(),
        new DynamicChipRecipeSerializer()
    );

    @Override
    @NotNull
    public DynamicChipRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
        val base = Ingredient.fromJson(jsonObject.get("base"));
        val marker = Ingredient.fromJson(jsonObject.get("marker"));
        val result = jsonObject.get("result").isJsonObject()
            ? ShapedRecipe.itemStackFromJson(jsonObject.get("result").getAsJsonObject())
            : BuiltInRegistries.ITEM.get(new ResourceLocation(jsonObject.get("result").toString())).getDefaultInstance();
        return new DynamicChipRecipe(id, base, marker, result);
    }

    @Override
    @NotNull
    public DynamicChipRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf friendlyByteBuf) {
        val base = Ingredient.fromNetwork(friendlyByteBuf);
        val marker = Ingredient.fromNetwork(friendlyByteBuf);
        val result = friendlyByteBuf.readItem();
        return new DynamicChipRecipe(id, base, marker, result);
    }

    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf, DynamicChipRecipe recipe) {
        recipe.base.toNetwork(friendlyByteBuf);
        recipe.marker.toNetwork(friendlyByteBuf);
        friendlyByteBuf.writeItem(recipe.result);
    }
}