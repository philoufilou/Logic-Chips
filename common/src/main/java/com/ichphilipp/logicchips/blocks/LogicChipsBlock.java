package com.ichphilipp.logicchips.blocks;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.items.LogicChipsItem;
import com.ichphilipp.logicchips.utils.RegistryMgr;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class LogicChipsBlock<T extends Block> implements Supplier<T> {
    private static final Map<String, LogicChipsBlock<?>> ALL = new LinkedHashMap<>();

    public static final LogicChipsBlock<ChipFrame> GATE_FRAME = new LogicChipsBlock<>(
        "gate_frame",
        ChipFrame::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER)
    );

    public static Map<String, LogicChipsBlock<?>> getAll() {
        return Collections.unmodifiableMap(ALL);
    }

    public final String name;
    public final RegistrySupplier<T> block;
    public final LogicChipsItem<BlockItem> item;

    @Override
    public T get() {
        return this.block.get();
    }

    public RegistrySupplier<BlockItem> item() {
        return this.item.item;
    }

    private LogicChipsBlock(
        String name,
        Function<BlockBehaviour.Properties, T> block,
        BlockBehaviour.Properties properties
    ) {
        this.name = name.toLowerCase(Locale.ROOT);
        if (ALL.containsKey(name)) {
            throw new IllegalArgumentException("already registered");
        }
        this.block = RegistryMgr.BLOCK.register(this.name, () -> block.apply(modifyProperties(properties)));
        this.item = LogicChipsItem.registerImpl(
            this.name,
            prop -> new BlockItem(this.block.get(), prop),
            LogicChips.defaultItemProperties()
        );
        ALL.put(this.name, this);
    }

    public ResourceLocation id() {
        return block.getId();
    }

    public ResourceKey<T> resourceKey() {
        return this.block.getKey();
    }

    public BlockBehaviour.Properties modifyProperties(BlockBehaviour.Properties properties) {
        return properties.setId(LogicChips.duck(resourceKey()));
    }
}
