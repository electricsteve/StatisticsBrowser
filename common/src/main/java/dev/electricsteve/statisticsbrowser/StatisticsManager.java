package dev.electricsteve.statisticsbrowser;

import com.google.gson.JsonElement;

import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticsManager {
    private static StatisticsManager INSTANCE;

    private final ConcurrentHashMap<UUID, StatsPlayer> statsMap;

    private StatisticsManager() {
        statsMap = new ConcurrentHashMap<>();
    }

    public static StatisticsManager getInstance() {
        if (INSTANCE == null) INSTANCE = new StatisticsManager();
        return INSTANCE;
    }

    public void addPlayer(ServerPlayer player) {
        statsMap.put(player.getUUID(), new StatsPlayer(player.getName().getString(), player.getStats()));
    }

    public JsonElement getPlayerData(UUID uuid) {
        return statsMap.get(uuid).serialize();
    }
}
