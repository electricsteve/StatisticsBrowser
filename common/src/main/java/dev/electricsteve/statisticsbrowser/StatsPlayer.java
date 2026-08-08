package dev.electricsteve.statisticsbrowser;

import com.google.gson.JsonObject;

import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.ServerStatsCounter;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

public record StatsPlayer(String name, UUID uuid, ServerStatsCounter statsCounter, boolean offline) {
    public JsonObject serialize() {
        return getJsonObject(statsCounter);
    }

    public JsonObject cloneFillAndSerialize(MinecraftServer server) {
        ServerStatsCounter filledStats = ServerStatsCounterUtils.fill(ServerStatsCounterUtils.clone(statsCounter, server));
        return getJsonObject(filledStats);
    }

    private @NonNull JsonObject getJsonObject(ServerStatsCounter stats) {
        JsonObject object = stats.toJson().getAsJsonObject();
        object.addProperty("name", name);
        object.addProperty("uuid", uuid.toString());
        return object;
    }
}
