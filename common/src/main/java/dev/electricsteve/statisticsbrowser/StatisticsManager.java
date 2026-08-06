package dev.electricsteve.statisticsbrowser;

import com.google.gson.JsonElement;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticsManager {
    private static StatisticsManager INSTANCE;

    private final ConcurrentHashMap<UUID, StatsPlayer> statsMap;
    private MinecraftServer minecraftServer;

    private StatisticsManager() {
        statsMap = new ConcurrentHashMap<>();
    }

    public static StatisticsManager getInstance() {
        if (INSTANCE == null) INSTANCE = new StatisticsManager();
        return INSTANCE;
    }


    /**
     * Set the minecraft server. Meant to be called on server start.
     *
     * @param server the MinecraftServer instance to set
     */
    public void setMinecraftServer(MinecraftServer server) {
        this.minecraftServer = server;
    }

    public MinecraftServer getMinecraftServer() {
        return this.minecraftServer;
    }

    public void addPlayer(ServerPlayer player) {
        statsMap.put(player.getUUID(), new StatsPlayer(player.getName().getString(), player.getStats()));
    }

    public JsonElement getPlayerData(UUID uuid) {
        return statsMap.get(uuid).serialize();
    }
}
