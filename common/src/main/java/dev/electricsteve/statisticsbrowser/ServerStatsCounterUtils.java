package dev.electricsteve.statisticsbrowser;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ServerStatsCounterUtils {
    public static ServerStatsCounter clone(ServerStatsCounter in, @NotNull MinecraftServer server) {
        ServerStatsCounter out = new ServerStatsCounter(server, in.file);
        out.stats.putAll(in.stats);
        return out;
    }

    public static ServerStatsCounter fill(ServerStatsCounter in) {
        for (Map.Entry<ResourceKey<StatType<?>>, StatType<?>> statTypeEntry : BuiltInRegistries.STAT_TYPE.entrySet()) {
            extracted(in, statTypeEntry);
        }
        return in;
    }

    private static <T> void extracted(ServerStatsCounter in, Map.Entry<ResourceKey<StatType<?>>, StatType<?>> statTypeEntry) {
        @SuppressWarnings("unchecked")
        StatType<T> statType = (StatType<T>) statTypeEntry.getValue();
        for (T thing : statType.getRegistry()) {
            Stat<T> stat = statType.get(thing);
            in.stats.putIfAbsent(stat, 0);
        }
    }
}
