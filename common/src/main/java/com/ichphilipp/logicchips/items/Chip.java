package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.blocks.ChipFrame;
import com.ichphilipp.logicchips.utils.*;
import java.util.List;

import lombok.val;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Chip extends Item {

    public Chip(Properties properties, ChipType types) {
        super(properties);
        ChipFrame.add(types, this);
    }

    public Chip(ChipType type) {
        this(LogicChips.DEFAULT_CHIP_PROP, type);
    }

    @Override
    public void appendHoverText(
        @NotNull ItemStack pStack,
        @Nullable Level pLevel,
        @NotNull List<Component> pTooltipComponents,
        @NotNull TooltipFlag pIsAdvanced
    ) {
        val langKey = Screen.hasShiftDown()
            ? String.format("tooltip.%s.%s", LogicChips.MOD_ID, pStack.getItem())
            : "tooltip.logicchips.shift";
        pTooltipComponents.add(Component.translatable(langKey));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
