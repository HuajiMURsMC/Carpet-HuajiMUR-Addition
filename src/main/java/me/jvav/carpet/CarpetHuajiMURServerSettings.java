package me.jvav.carpet;

import carpet.settings.Rule;
import carpet.settings.RuleCategory;

public class CarpetHuajiMURServerSettings {


    @Rule(
            desc = "Disable to send packets of particle to clients",
            category = {RuleCategory.SURVIVAL, RuleCategory.OPTIMIZATION}
    )
    public static boolean disableParticlesPackets = false;

    @Rule(
            desc = "Out the commands to the log and OPs",
            category = {RuleCategory.COMMAND}
    )
    public static boolean logCommands = false;
}
