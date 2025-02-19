package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.utils.RegistryMgr;
import dev.architectury.registry.registries.RegistrySupplier;
import lombok.val;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author ZZZank
 */
@SuppressWarnings("unused")
public final class LogicChipsItem<T extends Item> implements Supplier<T> {

    private static final Map<String, LogicChipsItem<? extends Item>> ALL = new LinkedHashMap<>();
    public static final LogicChipsItem<Item> CHIP =
        registerImpl("chip", Item::new, LogicChips.defaultItemProperties());
    //dual-input gate
    public static final LogicChipsItem<Chip> NOT_GATE = registerChip(ChipType.not);
    public static final LogicChipsItem<Chip> AND_GATE = registerChip(ChipType.and);
    public static final LogicChipsItem<Chip> NAND_GATE = registerChip(ChipType.nand);
    public static final LogicChipsItem<Chip> OR_GATE = registerChip(ChipType.or);
    public static final LogicChipsItem<Chip> NOR_GATE = registerChip(ChipType.nor);
    public static final LogicChipsItem<Chip> XOR_GATE = registerChip(ChipType.xor);
    public static final LogicChipsItem<Chip> XNOR_GATE = registerChip(ChipType.xnor);
    //tri-input gate
    public static final LogicChipsItem<Chip> AND_GATE_3 = registerChip(ChipType.and_3);
    public static final LogicChipsItem<Chip> NAND_GATE_3 = registerChip(ChipType.nand_3);
    public static final LogicChipsItem<Chip> OR_GATE_3 = registerChip(ChipType.or_3);
    public static final LogicChipsItem<Chip> NOR_GATE_3 = registerChip(ChipType.nor_3);
    public static final LogicChipsItem<Chip> XOR_GATE_3 = registerChip(ChipType.xor_3);
    public static final LogicChipsItem<Chip> XNOR_GATE_3 = registerChip(ChipType.xnor_3);
    //dynamic
    public static final LogicChipsItem<DynamicChip> DYNAMIC =
        registerImpl(ChipType.dynamic.toChipName(), DynamicChip::new, LogicChips.defaultChipProperties());

    public static Map<String, LogicChipsItem<? extends Item>> getAll() {
        return Collections.unmodifiableMap(ALL);
    }

    static LogicChipsItem<Chip> registerChip(@NotNull ChipType chipType) {
        return registerImpl(
            chipType.toChipName(),
            (prop) -> new Chip(prop, chipType),
            LogicChips.defaultChipProperties()
        );
    }

    public static <T extends Item> LogicChipsItem<T> registerImpl(
        @NotNull String name,
        @NotNull Function<Item.Properties, T> item,
        @NotNull Item.Properties properties
    ) {
        name = name.toLowerCase(Locale.ROOT);
        if (ALL.containsKey(name)) {
            throw new IllegalArgumentException("item registry name '" + name + "' already existed");
        }
        val registered = new LogicChipsItem<>(name, item, properties);
        ALL.put(name, registered);
        return registered;
    }

    public final String name;
    public final RegistrySupplier<T> item;

    private LogicChipsItem(String name, Function<Item.Properties, T> item, Item.Properties properties) {
        this.name = name;
        this.item = RegistryMgr.ITEM.register(name, () -> item.apply(this.modifyProperties(properties)));
    }

    @Override
    public T get() {
        return item.get();
    }

    public ResourceLocation id() {
        return item.getId();
    }

    public ResourceKey<T> resourceKey() {
        return this.item.getKey();
    }

    private Item.Properties modifyProperties(Item.Properties properties) {
        return properties.setId(LogicChips.duck(resourceKey()));
    }
}
