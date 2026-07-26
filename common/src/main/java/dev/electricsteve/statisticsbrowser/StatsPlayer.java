package dev.electricsteve.statisticsbrowser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.stats.ServerStatsCounter;

public record StatsPlayer(String name, ServerStatsCounter statsCounter) {
    public JsonElement serialize() {
        JsonObject object = statsCounter.toJson().getAsJsonObject();
        object.addProperty("name", name);
        return object;
    }
}
