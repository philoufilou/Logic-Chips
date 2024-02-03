package com.ichphilipp.logicchips.allitems;

import com.ichphilipp.logicchips.LogicChips;
import com.ichphilipp.logicchips.allitems.Chip;
import com.ichphilipp.logicchips.utils.GateFrameTypes;
import java.util.function.Supplier;
import me.shedaniel.architectury.registry.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

// import net.minecraftforge.eventbus.api.IEventBus;
// import net.minecraftforge.fml.RegistryObject;
// import net.minecraftforge.registries.DeferredRegister;
// import net.minecraftforge.registries.ForgeRegistries;

public class AllItems {

  public static final me.shedaniel.architectury.registry.Registry<Item> ITEM_REG = Registries
      .get(LogicChips.MOD_ID)
      .get(Registry.ITEM_REGISTRY);
  // base chip
  public static final Supplier<Item> CHIP = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "chip"),
      () -> new Item(new Item.Properties().tab(LogicChips.ITEM_GROUP)));
  // gates
  public static final Supplier<Item> NOT_GATE = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "not_gate"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.not));
  public static final Supplier<Item> AND_GATE = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "and_gate"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.and));
  public static final Supplier<Item> NAND_GATE = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "nand_gate"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.nand));
  public static final Supplier<Item> OR_GATE = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "or_gate"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.or));
  public static final Supplier<Item> NOR_GATE = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "nor_gate"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.nor));
  public static final Supplier<Item> XOR_GATE = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "xor_gate"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.xor));
  public static final Supplier<Item> XNOR_GATE = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "xnor_gate"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.xnor));
  // -------------------------------------------------------------------------------------------------------
  public static final Supplier<Item> AND_GATE_3 = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "and_gate_3"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.and_3));
  public static final Supplier<Item> NAND_GATE_3 = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "nand_gate_3"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.nand_3));
  public static final Supplier<Item> OR_GATE_3 = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "or_gate_3"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.or_3));
  public static final Supplier<Item> NOR_GATE_3 = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "nor_gate_3"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.nor_3));
  public static final Supplier<Item> XOR_GATE_3 = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "xor_gate_3"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.xor_3));
  public static final Supplier<Item> XNOR_GATE_3 = ITEM_REG.register(
      new ResourceLocation(LogicChips.MOD_ID, "xnor_gate_3"),
      () -> new Chip(
          new Item.Properties().tab(LogicChips.ITEM_GROUP).stacksTo(16),
          GateFrameTypes.xnor_3));

}
