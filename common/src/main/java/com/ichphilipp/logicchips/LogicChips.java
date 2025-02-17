package com.ichphilipp.logicchips;

import com.ichphilipp.logicchips.blocks.ChipFrameEntity;
import com.ichphilipp.logicchips.blocks.LogicChipsBlock;
import com.ichphilipp.logicchips.items.LogicChipsItem;
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
            () -> LogicChipsItem.AND_GATE.get().getDefaultInstance()// Icon
        )
    );

    private LogicChips() {
    }

    public static void init() {
        RegistryMgr.TABS.register();

        LogicChipsBlock.getAll();//trigger initialization
        RegistryMgr.BLOCK.register();

        LogicChipsItem.getAll();
        RegistryMgr.ITEM.register();

        ChipFrameEntity.TYPE.isPresent();
        RegistryMgr.BLOCK_ENTITY_TYPE.register();
    }

    public static ResourceLocation rl(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @SuppressWarnings("unchecked")
    public static <T> T duck(Object o) {
        return (T) o;
    }

    public static Item.Properties defaultItemProperties() {
        return new Item.Properties().arch$tab(TAB);
    }

    public static Item.Properties defaultChipProperties() {
        return defaultItemProperties().stacksTo(16);
    }
}
