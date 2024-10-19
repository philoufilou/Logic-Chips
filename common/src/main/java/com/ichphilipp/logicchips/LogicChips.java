package com.ichphilipp.logicchips;

import com.ichphilipp.logicchips.blocks.LogicChipsBlock;
import com.ichphilipp.logicchips.items.LogicChipsItems;
import com.ichphilipp.logicchips.utils.RegistryMgr;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class LogicChips {

    public static final String MOD_ID = "logicchips";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final RegistrySupplier<CreativeModeTab> TAB = RegistryMgr.TABS.register(
        "tab",
        () -> CreativeTabRegistry.create(
            Component.translatable("itemGroup.logicchips.tab"), // Tab Name
            () -> LogicChipsItems.AND_GATE.get().getDefaultInstance()// Icon
        )
    );
    public static final Item.Properties DEFAULT_ITEM_PROP = new Item.Properties().arch$tab(TAB);
    public static final Item.Properties DEFAULT_CHIP_PROP = new Item.Properties().arch$tab(TAB).stacksTo(16);

    private LogicChips() {}

    public static void init() {
        LogicChipsBlock.init();
        LogicChipsItems.getAll(); //trigger initialization
        RegistryMgr.init();
    }

    public static ResourceLocation rl(@NotNull String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
