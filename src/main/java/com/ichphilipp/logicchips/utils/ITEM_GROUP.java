package com.ichphilipp.logicchips.utils;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.allitems.AllItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ITEM_GROUP {
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(LogicChips.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(AllItems.AND_GATE.get());
        }
    };
}
