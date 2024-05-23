package com.ichphilipp.logicchips;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(LogicChips.MOD_ID)
public class LogicChipsNeoForge {

    public LogicChipsNeoForge(IEventBus bus) {
        LogicChips.init();
    }
}
