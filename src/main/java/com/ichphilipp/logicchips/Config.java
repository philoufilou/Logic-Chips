package com.ichphilipp.logicchips;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public final ForgeConfigSpec defaultConfig;
    public final ForgeConfigSpec.IntValue max_stack_size_chips;
    Config() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        max_stack_size_chips = builder.comment("Max Stack-Size for Chips [ default: 16 ]").defineInRange("max_stack_size_chips", 16, 1, 64);

        defaultConfig = builder.build();
    }
}
