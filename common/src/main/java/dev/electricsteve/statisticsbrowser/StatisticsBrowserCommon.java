package dev.electricsteve.statisticsbrowser;

import dev.electricsteve.statisticsbrowser.platform.Services;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class StatisticsBrowserCommon {
    public static void init() {
        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = "/web";
                staticFiles.location = Location.CLASSPATH;
            });
            config.routes.get("/api/status", ctx -> ctx.json("test"));
        }).start(7070);
    }
}