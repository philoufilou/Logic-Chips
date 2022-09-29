package com.ichphilipp.logicchips;

import com.ichphilipp.logicchips.allblocks.AllBlocks;
import com.ichphilipp.logicchips.allitems.AllItems;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(LogicChips.MODID)
public class LogicChips {
    public static final String MODID = "logicchips";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(LogicChips.MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(AllItems.AND_GATE.get());
        }
    };
    public LogicChips(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AllItems.register(eventBus);
        AllBlocks.register(eventBus);

        eventBus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(AllBlocks.GATE_FRAME.get(), RenderType.cutout()));
    }
}
