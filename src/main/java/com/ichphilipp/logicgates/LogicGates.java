package com.ichphilipp.logicgates;

import com.ichphilipp.logicgates.allblocks.AllBlocks;
import com.ichphilipp.logicgates.allitems.AllItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod(LogicGates.MOD_ID)
public class LogicGates{
    public static final String MOD_ID = "logicgates";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Config config = new Config();
    public static final ItemGroup ITEM_GROUP = (new ItemGroup(MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(AllItems.AND_GATE.get());
        }
    });
    public LogicGates() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, LogicGates.config.defaultConfig);

        AllItems.register(eventBus);
        AllBlocks.register(eventBus);

        eventBus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> RenderTypeLookup.setRenderLayer(AllBlocks.GATE_FRAME.get(), RenderType.cutout()));
    }
}
