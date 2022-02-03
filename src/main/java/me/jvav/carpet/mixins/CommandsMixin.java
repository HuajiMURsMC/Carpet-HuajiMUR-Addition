package me.jvav.carpet.mixins;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.jvav.carpet.CarpetHuajiMURAddition;
import me.jvav.carpet.CarpetHuajiMURServerSettings;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.chat.ChatType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Commands.class)
public class CommandsMixin {
    @Inject(method = "performCommand", at = @At("HEAD"))
    private void onHandleChat(CommandSourceStack commandSourceStack, String string, CallbackInfoReturnable<Integer> cir) {
        if (!CarpetHuajiMURServerSettings.logCommands) {
            return;
        }
        if (!string.startsWith("/")) {
            return;
        }
        String name;
        try {
            name = commandSourceStack.getPlayerOrException().getDisplayName().getString();
        } catch (CommandSyntaxException e) {
            name = "Console";
        }
        Component component = new TranslatableComponent("chat.type.text", name, string);
        CarpetHuajiMURAddition.LOGGER.info(component.getString());
        CarpetHuajiMURAddition.INSTANCE.broadcastToOps(component, ChatType.CHAT);
    }
}
