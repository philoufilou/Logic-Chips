package com.ichphilipp.logicchips;

import com.ichphilipp.logicchips.blocks.AllBlocks;
import com.ichphilipp.logicchips.items.LogicChipItem;
import me.shedaniel.architectury.registry.CreativeTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogicChips {

    public static final String MOD_ID = "logicchips";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final CreativeModeTab ITEM_GROUP = CreativeTabs.create(
        new ResourceLocation(MOD_ID, "tab"),
        () -> LogicChipItem.AND_GATE.get().getDefaultInstance()
    );

    private LogicChips() {
        //ClientLifecycleEvent.CLIENT_SETUP.register(new OnClientSetup());
    }

    public static void init() {
        AllBlocks.init();
        LogicChipItem.init();
    }
}
