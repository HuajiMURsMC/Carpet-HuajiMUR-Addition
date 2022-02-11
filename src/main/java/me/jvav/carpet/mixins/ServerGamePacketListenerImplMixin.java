package me.jvav.carpet.mixins;

import me.jvav.carpet.CarpetHuajiMURServerSettings;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.TextFilter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;
    private static String string;

    @Inject(
            method = "handleChat(Lnet/minecraft/server/network/TextFilter$FilteredText;)V",
            at = @At("HEAD")
    )
    private void beforeGetPlayerList(TextFilter.FilteredText filteredText, CallbackInfo ci) {
        string = filteredText.getRaw();
    }

    @ModifyArg(
            method = "handleChat(Lnet/minecraft/server/network/TextFilter$FilteredText;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/players/PlayerList;broadcastMessage(Lnet/minecraft/network/chat/Component;Ljava/util/function/Function;Lnet/minecraft/network/chat/ChatType;Ljava/util/UUID;)V"
            ),
            index = 0
    )
    private Component adjustComponent2(Component component2) {
        if (CarpetHuajiMURServerSettings.logChatWithoutPrefix) {
            return new TranslatableComponent("chat.type.text", this.player.getName(), string);
        }
        return component2;
    }
}
