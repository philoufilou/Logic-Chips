package com.ichphilipp.logicchips;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(LogicChips.MOD_ID)
public class LogicChipsNeoForge {

    public LogicChipsNeoForge(IEventBus bus) {
        LogicChips.init();
        NeoForge.EVENT_BUS.register(this);
    }
}
