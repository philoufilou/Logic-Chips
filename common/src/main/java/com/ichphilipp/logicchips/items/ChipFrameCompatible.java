package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.api.TriBoolLogic;

/**
 * @author ZZZank
 */
public interface ChipFrameCompatible {

    boolean acceptRedstoneAtLeft();

    boolean acceptRedstoneAtRear();

    boolean acceptRedstoneAtRight();

    TriBoolLogic logic();
}
