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
import org.apache.commons.io.IOUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;
import java.util.Objects;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        String dataJSON;
        try {
            dataJSON = IOUtils.toString(
                    Objects.requireNonNull(Translations.class.getClassLoader().getResourceAsStream(
                            String.format("assets/huajimur/lang/%s.json", lang))),
                    StandardCharsets.UTF_8);
        } catch (NullPointerException | IOException e) {
            return null;
        }
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.fromJson(dataJSON, new TypeToken<Map<String, String>>() {
        }.getType());
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
