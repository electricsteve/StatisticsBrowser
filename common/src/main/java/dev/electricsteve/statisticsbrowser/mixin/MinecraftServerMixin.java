package dev.electricsteve.statisticsbrowser.mixin;

import net.minecraft.server.MinecraftServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.electricsteve.statisticsbrowser.StatisticsManager;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;initServer()Z"), method = "runServer")
    private void beforeSetupServer(CallbackInfo info) {
        StatisticsManager.getInstance().setMinecraftServer((MinecraftServer) (Object) this);
    }
}
