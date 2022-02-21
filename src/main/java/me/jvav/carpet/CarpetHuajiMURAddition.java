package me.jvav.carpet;

import carpet.CarpetServer;
import carpet.CarpetExtension;
import carpet.utils.Translations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.players.PlayerList;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarpetHuajiMURAddition implements CarpetExtension, ModInitializer {

    public static final CarpetHuajiMURAddition INSTANCE = new CarpetHuajiMURAddition();
    public static final Logger LOGGER = LoggerFactory.getLogger("Carpet-HuajiMUR-Addition");

    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(INSTANCE);
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(CarpetHuajiMURServerSettings.class);
    }

    public void broadcastToOps(Component component, ChatType chatType) {
        PlayerList playerList = CarpetServer.minecraft_server.getPlayerList();
        playerList.getPlayers().forEach(
                player -> {
                    if (playerList.isOp(player.getGameProfile())) {
                        player.sendMessage(component, chatType, player.getUUID());
                    }
                }
        );
    }
}
