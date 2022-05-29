package com.spiritlight.wynnstats;

import akka.Main;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigSpirit {
    public static void getConfig() throws IOException {
        File config = new File("config/WynnStats.json");
        if (config.exists()) {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject)parser.parse(new FileReader("config/WynnStats.json"));
            for (JsonElement element : jsonObject.getAsJsonArray("guildLists")) {
                MainMod.guildLists.add(element.getAsString());
            }
            // a = String.valueOf(jsonObject.get("g")).replace("\"", ""); // Funny bug!
        } else {
            writeConfig();
        }
    }

    public static void writeConfig() throws IOException {
        JsonWriter writer = new JsonWriter(new FileWriter("config/WynnStats.json"));
        writer.beginObject();
        writer.name("guilds");
        writer.beginArray();
        for (String g : MainMod.guildLists) {
            writer.value(g);
        }
        writer.endArray();
        writer.endObject();
        writer.close();
    }
}