package dev.electricsteve.statisticsbrowser;

import dev.electricsteve.statisticsbrowser.platform.Services;

public class StatisticsBrowserCommon {
    public static void init() {
        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
    }
}