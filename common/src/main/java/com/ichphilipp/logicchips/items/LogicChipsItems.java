package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.utils.ChipType;
import com.ichphilipp.logicchips.utils.RegistryMgr;
import dev.architectury.registry.registries.RegistrySupplier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author ZZZank
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogicChipsItems {

    private static final Map<String, RegistrySupplier<? extends Item>> ALL = new LinkedHashMap<>();
    public static final RegistrySupplier<Item> CHIP = register("chip");
    //dual-input gate
    public static final RegistrySupplier<Chip> NOT_GATE = registerChip(ChipType.not);
    public static final RegistrySupplier<Chip> AND_GATE = registerChip(ChipType.and);
    public static final RegistrySupplier<Chip> NAND_GATE = registerChip(ChipType.nand);
    public static final RegistrySupplier<Chip> OR_GATE = registerChip(ChipType.or);
    public static final RegistrySupplier<Chip> NOR_GATE = registerChip(ChipType.nor);
    public static final RegistrySupplier<Chip> XOR_GATE = registerChip(ChipType.xor);
    public static final RegistrySupplier<Chip> XNOR_GATE = registerChip(ChipType.xnor);
    //tri-input gate
    public static final RegistrySupplier<Chip> AND_GATE_3 = registerChip(ChipType.and_3);
    public static final RegistrySupplier<Chip> NAND_GATE_3 = registerChip(ChipType.nand_3);
    public static final RegistrySupplier<Chip> OR_GATE_3 = registerChip(ChipType.or_3);
    public static final RegistrySupplier<Chip> NOR_GATE_3 = registerChip(ChipType.nor_3);
    public static final RegistrySupplier<Chip> XOR_GATE_3 = registerChip(ChipType.xor_3);
    public static final RegistrySupplier<Chip> XNOR_GATE_3 = registerChip(ChipType.xnor_3);

    public static Map<String, RegistrySupplier<? extends Item>> getAll() {
        return Collections.unmodifiableMap(ALL);
    }

    public static RegistrySupplier<Item> register(@NotNull String name) {
        return registerImpl(name, () -> new Item(LogicChips.DEFAULT_ITEM_PROP));
    }

    public static RegistrySupplier<Chip> registerChip(@NotNull String name, @NotNull ChipType chipType) {
        return registerImpl(name, () -> new Chip(chipType));
    }

    public static RegistrySupplier<Chip> registerChip(@NotNull ChipType chipType) {
        val typeName = chipType.name().toLowerCase(Locale.ROOT);
        val name = typeName.endsWith("_3")
            ? typeName.replace("_3", "_gate_3") //or_3 -> or_gate_3
            : typeName + "_gate";
        return registerImpl(name, () -> new Chip(chipType));
    }

    private static <T extends Item> RegistrySupplier<T> registerImpl(
        @NotNull String name,
        @NotNull Supplier<T> supplier
    ) {
        preCondition(name);
        val registered = RegistryMgr.registerItem(name, supplier);
        ALL.put(name, registered);
        return registered;
    }

    private static void preCondition(String name) {
        if (name == null) {
            throw new IllegalArgumentException("item registry name should not be null");
        }
        if (ALL.containsKey(name)) {
            throw new IllegalArgumentException("item registry name '" + name + "' already existed");
        }
        val tryBuild = new ResourceLocation(LogicChips.MOD_ID, name);
    }
}
