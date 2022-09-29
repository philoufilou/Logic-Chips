package com.ichphilipp.logicchips;

import com.ichphilipp.logicchips.allblocks.AllBlocks;
import com.ichphilipp.logicchips.allitems.AllItems;
import com.ichphilipp.logicchips.utils.ChipConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(LogicChips.MODID)
public class LogicChips {
    public static final String MODID = "logicchips";
    public static final Logger LOGGER = LogUtils.getLogger();
    public LogicChips(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ChipConfig.defaultConfig);

        AllItems.register(modEventBus);
        AllBlocks.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);
    }
}
