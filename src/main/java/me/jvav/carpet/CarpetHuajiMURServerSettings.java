package me.jvav.carpet;

import carpet.settings.Rule;
import carpet.settings.RuleCategory;

public class CarpetHuajiMURServerSettings {


    @Rule(
            desc = "禁止服务器发送粒子到客户端",
            category = {RuleCategory.CLIENT, RuleCategory.OPTIMIZATION}
    )
    public static boolean disableParticlesPackets = false;

    @Rule(
            desc = "输出指令到日志",
            category = {RuleCategory.COMMAND}
    )
    public static boolean logCommands = false;

    @Rule(
            desc = "输出指令到OP",
            category = {RuleCategory.COMMAND}
    )
    public static boolean logCommandsToOps = false;

    @Rule(
            desc = "暴力修复NoCom",
            category = {RuleCategory.BUGFIX}
    )
    public static boolean antiNoCom = false;

    @Rule(
            desc = "输出玩家聊天时不包含前缀(搭配MCDR用的)",
            category = {RuleCategory.FEATURE}
    )
    public static boolean logChatWithoutPrefix = false;
}
