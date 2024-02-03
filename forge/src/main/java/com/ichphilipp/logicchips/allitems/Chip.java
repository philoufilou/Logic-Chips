package com.ichphilipp.logicchips.allitems;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.utils.*;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import com.ichphilipp.logicchips.allblocks.ChipFrame;
// import net.minecraft.client.gui.screen.Screen;
// import net.minecraft.client.util.ITooltipFlag;
// import net.minecraft.item.Item;
// import net.minecraft.item.ItemStack;
// import net.minecraft.util.text.ITextComponent;
// import net.minecraft.util.text.TranslationTextComponent;
// import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.List;
public class Chip extends Item {
    public Chip(Properties properties, GateFrameTypes types) {
        super(properties);
        ChipFrame.add(types,this);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(new TranslatableComponent(String.format("tooltip.%s.%s", LogicChips.MOD_ID, pStack.getItem())));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.shift"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
