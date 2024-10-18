package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.api.TriBoolLogic;
import lombok.val;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author ZZZank
 */
public class DynamicChip extends Chip {

    public DynamicChip(Properties properties) {
        super(properties, ChipType.dynamic);
    }

    @Nullable
    public static TriBoolLogic readLogicFromName(@NotNull Component displayName) {
        val string = displayName.getString();
        if (string.length() < 8) {
            return null;
        }
        val logics = new boolean[8];
        for (int i = 0; i < 8; i++) {
            val c = string.charAt(i);
            if (c == '0') {
                logics[i] = false;
            } else if (c == '1') {
                logics[i] = true;
            } else {
                return null;
            }
        }
        return (left, middle, right) -> left
            ? middle ? right ? logics[0] : logics[1] : right ? logics[2] : logics[3]
            : middle ? right ? logics[4] : logics[5] : right ? logics[6] : logics[7];
    }
}
