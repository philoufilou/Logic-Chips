package com.ichphilipp.logicchips;

import com.ichphilipp.logicchips.blocks.AllBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class LogicChipsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.GATE_FRAME.get(), RenderType.cutout());
    }
}
