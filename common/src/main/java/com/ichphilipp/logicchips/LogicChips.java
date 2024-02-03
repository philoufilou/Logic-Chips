package com.ichphilipp.logicchips;

import com.ichphilipp.logicchips.blocks.AllBlocks;
import com.ichphilipp.logicchips.items.AllItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import me.shedaniel.architectury.registry.CreativeTabs;
import me.shedaniel.architectury.event.events.client.ClientLifecycleEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogicChips {
    public static final String MOD_ID = "logicchips";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final CreativeModeTab ITEM_GROUP = CreativeTabs.create(
        new ResourceLocation(MOD_ID, "tab"), ()->new ItemStack(AllItems.AND_GATE.get())
    );
    public LogicChips() {
        // IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ClientLifecycleEvent.CLIENT_SETUP.register(new OnClientSetup());

        // eventBus.addListener(this::onClientSetup);
        // MinecraftForge.EVENT_BUS.register(this);
    }

    private class OnClientSetup implements ClientLifecycleEvent.ClientState {
        @Override
        public void stateChanged(Minecraft instance) {
            // ItemBlockRenderTypes.setRenderLayer(AllBlocks.GATE_FRAME.get(), RenderType.cutout());
        }
    }
}
