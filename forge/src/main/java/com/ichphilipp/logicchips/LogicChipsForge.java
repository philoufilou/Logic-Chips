package com.ichphilipp.logicchips;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.ichphilipp.logicchips.blocks.AllBlocks;

import me.shedaniel.architectury.event.events.client.ClientLifecycleEvent;
import me.shedaniel.architectury.platform.forge.EventBuses;

@Mod(LogicChips.MOD_ID)
public class LogicChipsForge {

    public LogicChipsForge() {
        EventBuses.registerModEventBus(LogicChips.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::onClientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(AllBlocks.GATE_FRAME.get(), RenderType.cutout()));
    }
}
