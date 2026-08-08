package dev.electricsteve.statisticsbrowser;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.ProfileResolver;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.world.level.storage.LevelResource;

import org.jspecify.annotations.NonNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import dev.electricsteve.statisticsbrowser.types.PlayerInfo;

public class StatisticsManager {
    private static StatisticsManager INSTANCE;

    private final ConcurrentHashMap<UUID, StatsPlayer> statsMap;
    private MinecraftServer minecraftServer;
    private boolean offlineAdded = false;

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
        statsMap.put(player.getUUID(), new StatsPlayer(player.getName().getString(), player.getUUID(), player.getStats(), false));
    }

    /// Attempts to load stats for all offline players. It does this by loading data from all
    /// JSON files in the stats directory
    public void addOfflinePlayers() {
        if (this.offlineAdded) return;
        if (this.minecraftServer == null) {
            Constants.LOG.error("MinecraftServer is null, cannot add offline players.");
            return;
        }
        Path statFolder = this.minecraftServer.getWorldPath(LevelResource.PLAYER_STATS_DIR);
        try (Stream<Path> paths = Files.walk(statFolder)) {
            paths.filter(Files::isRegularFile).forEach(path -> {
                String fileName = path.getFileName().toString();
                if (fileName.endsWith(".json")) {
                    String uuidString = fileName.substring(0, fileName.length() - 5);
                    try {
                        UUID uuid = UUID.fromString(uuidString);
                        if (!statsMap.containsKey(uuid)) {
                            ServerStatsCounter statsCounter = new ServerStatsCounter(this.minecraftServer, path);
                            ProfileResolver resolver = this.minecraftServer.services().profileResolver();
                            Optional<GameProfile> profile = resolver.fetchById(uuid);
                            if (profile.isPresent()) {
                                String name = profile.get().name();
                                statsMap.put(uuid, new StatsPlayer(name, uuid, statsCounter, true));
                            } else {
                                statsMap.put(uuid, new StatsPlayer(uuid.toString(), uuid, statsCounter, true));
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        Constants.LOG.warn("Invalid UUID in stats folder: {}", uuidString);
                    }
                }
            });
        } catch (Exception e) {
            Constants.LOG.error("Error while adding offline players: ", e);
        }
        this.offlineAdded = true;
    }

    public @NonNull List<PlayerInfo> getPlayerList(boolean includeOffline, boolean onlyOffline) {
        if (onlyOffline) includeOffline = true;
        List<PlayerInfo> playerInfoList = new ArrayList<>();
        if (includeOffline) {
            addOfflinePlayers();
        }
        boolean finalIncludeOffline = includeOffline;
        this.statsMap.forEach((uuid, statsPlayer) -> {
            if ((onlyOffline && !statsPlayer.offline()) || (!finalIncludeOffline && statsPlayer.offline())) return;
            playerInfoList.add(new PlayerInfo(statsPlayer.name(), statsPlayer.uuid(), statsPlayer.offline()));
        });
        return playerInfoList;
    }

    /// Get raw player stats from StatsCounter
    public JsonObject getRawPlayerStats(UUID uuid) {
        return statsMap.get(uuid).serialize();
    }

    /// Get raw player stats, but padded with all custom stats, even if they are 0.
    public JsonObject getFilledPlayerStats(UUID uuid) {
        return statsMap.get(uuid).cloneFillAndSerialize(this.minecraftServer);
    }
}
