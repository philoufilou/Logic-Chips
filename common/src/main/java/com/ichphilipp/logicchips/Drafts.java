package com.ichphilipp.logicchips;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ichphilipp.logicchips.items.ChipType;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author ZZZank
 */
public class Drafts {
    static void print(Object... o) {
        System.out.println(Arrays.toString(o));
    }

    public static void main(String[] args) {
        val gson = new Gson();
        for (val chipType : ChipType.values()) {
            val path = Path.of("./.compile_output/items/%s.json".formatted(chipType.chipName));
            try (val writer = Files.newBufferedWriter(path)) {
                val jsonWriter = gson.newJsonWriter(writer);
                jsonWriter.setIndent("    ");
                val toWrite = make(new JsonObject(), o -> {
                    o.add("model", make(new JsonObject(), o2 -> {
                        o2.addProperty("type", "minecraft:model");
                        o2.addProperty("model", "%s:item/%s".formatted(LogicChips.MOD_ID, chipType.chipName));
                    }));
                });
                gson.toJson(toWrite, jsonWriter);
            } catch (IOException e) {
                print(e.getMessage());
            }
        }
        print(Path.of("./.compile_output").toAbsolutePath());
    }

    static <T> T make(T o, Consumer<T> modifier) {
        modifier.accept(o);
        return o;
    }
}
