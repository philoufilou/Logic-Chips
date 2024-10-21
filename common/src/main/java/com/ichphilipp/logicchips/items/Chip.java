package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.LogicChips;

import java.util.List;

import lombok.val;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Chip extends Item {

    public final ChipType type;

    public Chip(ChipType type) {
        super(LogicChips.DEFAULT_CHIP_PROP);
        this.type = type;
    }

    @Override
    public void appendHoverText(
        @NotNull ItemStack stack,
        @Nullable Level level,
        @NotNull List<Component> tooltips,
        @NotNull TooltipFlag flag
    ) {
        val langKey = Screen.hasShiftDown()
            ? String.format("tooltip.%s.%s", LogicChips.MOD_ID, stack.getItem())
            : "tooltip.logicchips.shift";
        tooltips.add(new TranslatableComponent(langKey));
        super.appendHoverText(stack, level, tooltips, flag);
    }
}
