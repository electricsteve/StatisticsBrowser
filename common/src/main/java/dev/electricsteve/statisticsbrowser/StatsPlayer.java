package dev.electricsteve.statisticsbrowser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.stats.ServerStatsCounter;

import java.util.UUID;

public record StatsPlayer(String name, UUID uuid, ServerStatsCounter statsCounter, boolean offline) {
    public JsonElement serialize() {
        JsonObject object = statsCounter.toJson().getAsJsonObject();
        object.addProperty("name", name);
        object.addProperty("uuid", uuid.toString());
        return object;
    }
}
