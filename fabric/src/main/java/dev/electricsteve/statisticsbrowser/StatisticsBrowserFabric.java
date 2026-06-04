package dev.electricsteve.statisticsbrowser;

import net.fabricmc.api.ModInitializer;

public class StatisticsBrowserFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        StatisticsBrowserCommon.init();
    }
}
