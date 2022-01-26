package me.jvav.carpet.mixins.server;

import me.jvav.carpet.CarpetHuajiMURAddition;
import me.jvav.carpet.CarpetHuajiMURServerSettings;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.ChatType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DedicatedServer.class)
public class DedicatedServerMixin {

    @Inject(method = "handleConsoleInput", at = @At("HEAD"))
    private void onHandleConsoleInput(String string, CommandSourceStack commandSourceStack, CallbackInfo ci) {
        if (!CarpetHuajiMURServerSettings.logCommands) {
            return;
        }
        String command = string;
        if (!string.startsWith("/")) {
            command = "/" + string;
        }
        String message = "[Console] " + command;
        CarpetHuajiMURAddition.LOGGER.info(message);
        CarpetHuajiMURAddition.INSTANCE.broadcastToOps(new TextComponent(message), ChatType.CHAT);
    }
}
