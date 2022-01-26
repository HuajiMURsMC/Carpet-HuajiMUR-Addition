package me.jvav.carpet.mixins.server;

import me.jvav.carpet.CarpetHuajiMURAddition;
import me.jvav.carpet.CarpetHuajiMURServerSettings;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.TextFilter.FilteredText;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;

    @Inject(method = "handleChat(Lnet/minecraft/server/network/TextFilter$FilteredText;)V", at = @At("HEAD"))
    private void onHandleChat(FilteredText filteredText, CallbackInfo ci) {
        if (!CarpetHuajiMURServerSettings.logCommands) {
            return;
        }
        String string = filteredText.getRaw();
        if (!string.startsWith("/")) {
            return;
        }
        Component component = new TranslatableComponent("chat.type.text", this.player.getDisplayName(), string);
        CarpetHuajiMURAddition.LOGGER.info(component.getString());
        CarpetHuajiMURAddition.INSTANCE.broadcastToOps(component, ChatType.CHAT);
    }
}
