package dev.electricsteve.statisticsbrowser.types;

import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class PlayerInfo {
    public String name;
    public UUID uuid;

    public PlayerInfo(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public PlayerInfo(ServerPlayer player) {
        this.name = player.getName().getString();
        this.uuid = player.getUUID();
    }
}
