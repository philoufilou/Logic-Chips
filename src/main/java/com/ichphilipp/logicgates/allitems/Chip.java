package com.ichphilipp.logicgates.allitems;

import com.ichphilipp.logicgates.LogicGates;
import com.ichphilipp.logicgates.utils.*;
import com.ichphilipp.logicgates.allblocks.ChipFrame;
import com.ichphilipp.logicgates.utils.GateFrameTypes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.List;
public class Chip extends Item {
    public Chip(Properties properties, GateFrameTypes types, TriFunction<Boolean,Boolean,Boolean,Boolean> TRI, int[] ints) {
        super(properties);
        ChipFrame.add(types,this, TRI, ints);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable World pLevel, @NotNull List<ITextComponent> pTooltipComponents, @NotNull ITooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(new TranslationTextComponent(String.format("tooltip.%s.%s", LogicGates.MOD_ID, pStack.getItem())));
        } else {
            pTooltipComponents.add(new TranslationTextComponent("tooltip.shift"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
