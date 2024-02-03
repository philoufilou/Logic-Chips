package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.utils.GateFrameTypes;
import java.util.function.Supplier;
import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class AllItems {

        public static void init() {
                ITEM_REG.register();
        }

        public static final DeferredRegister<Item> ITEM_REG = DeferredRegister.create(

}
