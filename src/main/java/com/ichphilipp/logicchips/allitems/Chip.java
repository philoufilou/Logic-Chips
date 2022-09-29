package com.ichphilipp.logicchips.allitems;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.allblocks.ChipFrame;
import com.ichphilipp.logicchips.utils.GateFrameTypes;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Chip extends Item {
    public Chip(Item.Properties properties, GateFrameTypes types) {
        super(properties);
        ChipFrame.add(types,this);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if(Screen.hasShiftDown()) {
            components.add(Component.translatable(String.format("tooltip.%s.%s", LogicChips.MODID, stack.getItem())));
        } else {
            components.add(Component.translatable("tooltip.shift"));
        }
        super.appendHoverText(stack, level, components, flag);
    }
}