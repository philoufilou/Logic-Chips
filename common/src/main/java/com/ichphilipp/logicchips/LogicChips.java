package com.ichphilipp.logicchips;

import com.ichphilipp.logicchips.blocks.LogicChipsBlock;
import com.ichphilipp.logicchips.items.LogicChipsItem;
import com.ichphilipp.logicchips.utils.RegistryMgr;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public static final Item.Properties DEFAULT_ITEM_PROP = new Item.Properties().arch$tab(TAB);
    public static final Item.Properties DEFAULT_CHIP_PROP = new Item.Properties().arch$tab(TAB).stacksTo(16);

    private LogicChips() {}

    public static void init() {
        RegistryMgr.init();
        LogicChipsBlock.init();
        LogicChipsItem.init();
    }
}
