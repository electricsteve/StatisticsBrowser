package dev.electricsteve.statisticsbrowser.types;

import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public record PlayerInfo(String name, UUID uuid, boolean offline) {
    public PlayerInfo(ServerPlayer player) {
        this(player.getName().getString(), player.getUUID(), false);
    }
}
