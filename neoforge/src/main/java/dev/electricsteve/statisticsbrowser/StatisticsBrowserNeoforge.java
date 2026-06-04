package dev.electricsteve.statisticsbrowser;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class StatisticsBrowserNeoforge {

    public StatisticsBrowserNeoforge(IEventBus eventBus) {
        Constants.LOG.info("Hello NeoForge world!");
        StatisticsBrowserCommon.init();
    }
}