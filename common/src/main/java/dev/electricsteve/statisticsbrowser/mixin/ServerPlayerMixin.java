package dev.electricsteve.statisticsbrowser.mixin;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.electricsteve.statisticsbrowser.StatisticsManager;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(method = "<init>", at = @org.spongepowered.asm.mixin.injection.At("RETURN"))
    private void onInit(MinecraftServer server, ServerLevel level, GameProfile gameProfile, ClientInformation clientInformation, CallbackInfo ci) {
        StatisticsManager.getInstance().addPlayer((ServerPlayer) (Object) this);
    }
}
