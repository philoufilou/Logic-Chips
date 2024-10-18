package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.api.TriBoolLogic;
import lombok.val;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ZZZank
 */
public class DynamicChip extends Chip {

    public DynamicChip(Properties properties) {
        super(properties, ChipType.dynamic);
    }

    @Override
    public void appendHoverText(
        @NotNull ItemStack stack,
        @Nullable Level level,
        @NotNull List<Component> tooltips,
        @NotNull TooltipFlag flag
    ) {
        super.appendHoverText(stack, level, tooltips, flag);
        val logicData = readLogicFromName(stack.getHoverName());
        if (logicData == null) {
            return;
        }
        val logic = buildLogic(logicData);
        val allBool = new boolean[]{true, false};
        for (val left : allBool) {
            for (val mid : allBool) {
                for (val right : allBool) {
                    tooltips.add(Component.literal(String.format(
                        "%s + %s + %s -> %s",
                        representBool(left),
                        representBool(mid),
                        representBool(right),
                        representBool(logic.apply(left, mid, right))
                    )));
                }
            }
        }
    }

    private static String representBool(boolean b) {
        return b ? "█" : "░";
    }

    public static boolean @Nullable [] readLogicFromName(@NotNull Component displayName) {
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
        return logics;
    }

    private static TriBoolLogic buildLogic(boolean[] logicData) {
        if (logicData == null || logicData.length != 8) {
            throw new IllegalArgumentException();
        }
        return (left, middle, right) -> left
            ? middle ? right ? logicData[0] : logicData[1] : right ? logicData[2] : logicData[3]
            : middle ? right ? logicData[4] : logicData[5] : right ? logicData[6] : logicData[7];
    }
}
