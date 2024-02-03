package com.ichphilipp.logicchips;

import com.ichphilipp.logicchips.blocks.AllBlocks;
import com.ichphilipp.logicchips.items.AllItems;
import me.shedaniel.architectury.event.events.client.ClientLifecycleEvent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;

public class LogicChipsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        AllBlocks.init();
        AllItems.init();
    }

    private class OnClientSetup implements ClientLifecycleEvent.ClientState {

        @Override
        public void stateChanged(Minecraft instance) {
            // ItemBlockRenderTypes.setRenderLayer(AllBlocks.GATE_FRAME.get(), RenderType.cutout());
        }
    }
}
