package me.jvav.carpet.mixins;

import me.jvav.carpet.CarpetHuajiMURServerSettings;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Inject(method = "sendParticles(Lnet/minecraft/server/level/ServerPlayer;ZDDDLnet/minecraft/network/protocol/Packet;)Z", at = @At("HEAD"), cancellable = true)
    private void onSendParticles(ServerPlayer serverPlayer, boolean bl, double d, double e, double f, Packet<?> packet, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetHuajiMURServerSettings.disableParticlesPackets) {
            cir.setReturnValue(true);
        }
    }
}
