package com.ichphilipp.logicchips;

import com.ichphilipp.logicchips.items.AllItems;
import me.shedaniel.architectury.event.events.client.ClientLifecycleEvent;
import me.shedaniel.architectury.registry.CreativeTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogicChips {

    public static final String MOD_ID = "logicchips";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final CreativeModeTab ITEM_GROUP = CreativeTabs.create(
        new ResourceLocation(MOD_ID, "tab"),
        () -> new ItemStack(AllItems.AND_GATE.get())
    );

    public LogicChips() {
        ClientLifecycleEvent.CLIENT_SETUP.register(new OnClientSetup());
    }

    private class OnClientSetup implements ClientLifecycleEvent.ClientState {

        @Override
        public void stateChanged(Minecraft instance) {
            // TODO: should we setRenderLayer() ?
            // ItemBlockRenderTypes.setRenderLayer(AllBlocks.GATE_FRAME.get(), RenderType.cutout());
        }
    }
}
