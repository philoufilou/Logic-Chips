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
            tooltips.add(Component.literal("Rename in an anvil to setup custom logic. Valid name must starts with '0' or '1' numbers, 8 numbers in total"));
            return;
        }
        val logic = buildLogic(logicData);
        val allBool = new boolean[]{true, false};
        for (val left : allBool) {
            for (val mid : allBool) {
                for (val right : allBool) {
                    val tip = selectSignal(left, SIGNAL_LEFT)
                        .append(" + ")
                        .append(selectSignal(mid, SIGNAL_MID))
                        .append(" + ")
                        .append(selectSignal(right, SIGNAL_RIGHT))
                        .append(" -> ")
                        .append(selectSignal(logic.apply(left, mid, right), SIGNAL_OUT))
                    ;
                    tooltips.add(tip);
                }
            }
        }
    }

    private static final MutableComponent SIGNAL_OFF = signalComponent(ChatFormatting.GRAY);
    private static final MutableComponent SIGNAL_LEFT = signalComponent(ChatFormatting.AQUA);
    private static final MutableComponent SIGNAL_MID = signalComponent(ChatFormatting.LIGHT_PURPLE);
    private static final MutableComponent SIGNAL_RIGHT = signalComponent(ChatFormatting.YELLOW);
    private static final MutableComponent SIGNAL_OUT = signalComponent(ChatFormatting.RED);

    private static MutableComponent selectSignal(boolean condition, MutableComponent whenTrue) {
        return condition ? whenTrue : SIGNAL_OFF;
    }

    private static MutableComponent signalComponent(ChatFormatting color) {
        return Component.literal("█").setStyle(Style.EMPTY.withColor(color));
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
