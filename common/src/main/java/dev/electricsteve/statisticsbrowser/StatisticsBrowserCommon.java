package dev.electricsteve.statisticsbrowser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

import dev.electricsteve.statisticsbrowser.platform.Services;
import dev.electricsteve.statisticsbrowser.types.PlayerInfo;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.json.JsonMapper;

public class StatisticsBrowserCommon {
    public static void init() {
        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        Gson gson = new GsonBuilder().create();
        JsonMapper gsonMapper = new JsonMapper() {
            @Override
            public @NonNull String toJsonString(@NotNull Object obj, @NotNull Type type) {
                return gson.toJson(obj, type);
            }

            @Override
            public <T> @NonNull T fromJsonString(@NotNull String json, @NotNull Type targetType) {
                return gson.fromJson(json, targetType);
            }
        };
        Javalin app = Javalin.create(config -> {
            config.jsonMapper(gsonMapper);
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = "/web";
                staticFiles.location = Location.CLASSPATH;
            });
            config.routes.get("/api/status", ctx -> ctx.json("ok"));
            config.routes.get("/api/raw/{uuid}", ctx -> {
                UUID uuid = UUID.fromString(ctx.pathParam("uuid"));
                ctx.json(StatisticsManager.getInstance().getRawPlayerStats(uuid));
            });
            config.routes.get("/api/filled/{uuid}", ctx -> {
                UUID uuid = UUID.fromString(ctx.pathParam("uuid"));
                ctx.json(StatisticsManager.getInstance().getFilledPlayerStats(uuid));
            });
            config.routes.get("/api/playerhead/{uuid}", ctx -> {
                UUID uuid = UUID.fromString(ctx.pathParam("uuid"));
                ctx.result(PlayerHeadManager.getInstance().getHeadImage(uuid));
                ctx.contentType("image/png");
            });
            config.routes.get("/api/playerlist", ctx -> {
                boolean offlinePlayersIncluded = ctx.queryParamAsClass("offline", Boolean.class).getOrDefault(false);
                boolean onlyOfflinePlayers = ctx.queryParamAsClass("onlyOffline", Boolean.class).getOrDefault(false);
                List<PlayerInfo> playerInfoList = StatisticsManager.getInstance().getPlayerList(offlinePlayersIncluded, onlyOfflinePlayers);
                ctx.json(playerInfoList);
            });
        }).start(7070);
    }
}