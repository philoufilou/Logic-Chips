package com.ichphilipp.logicchips;

import net.minecraft.client.Minecraft;
import me.shedaniel.architectury.event.events.client.ClientLifecycleEvent;
import net.fabricmc.api.ModInitializer;

public class LogicChipsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
    }

    private class OnClientSetup implements ClientLifecycleEvent.ClientState {
        @Override
        public void stateChanged(Minecraft instance) {
            // ItemBlockRenderTypes.setRenderLayer(AllBlocks.GATE_FRAME.get(), RenderType.cutout());
        }
    }
}
