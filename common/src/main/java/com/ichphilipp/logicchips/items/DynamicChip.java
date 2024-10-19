package com.ichphilipp.logicchips.items;

import com.ichphilipp.logicchips.api.TriBoolLogic;
import lombok.val;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author ZZZank
 */
public class DynamicChip extends Chip {

    public DynamicChip() {
        super(ChipType.dynamic);
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
                    val tip = Component.empty()
                        .append(selectSignal(left, ChatFormatting.AQUA))
                        .append(" + ")
                        .append(selectSignal(mid, ChatFormatting.LIGHT_PURPLE))
                        .append(" + ")
                        .append(selectSignal(right, ChatFormatting.YELLOW))
                        .append(" -> ")
                        .append(selectSignal(logic.apply(left, mid, right), ChatFormatting.RED))
                    ;
                    tooltips.add(tip);
                }
            }
        }
    }

    private static MutableComponent selectSignal(boolean condition, ChatFormatting color) {
        return Component.literal("█")
            .setStyle(Style.EMPTY.withColor(condition ? color : ChatFormatting.GRAY));
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
        if (logicData == null || logicData.length < 8) {
            throw new IllegalArgumentException();
        }
        val logics = Arrays.copyOf(logicData, 8);
        return (left, middle, right) -> left
            ? middle ? right ? logics[0] : logics[1] : right ? logics[2] : logics[3]
            : middle ? right ? logics[4] : logics[5] : right ? logics[6] : logics[7];
    }
}
