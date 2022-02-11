package me.jvav.carpet;

import net.minecraft.server.level.TicketType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;

import java.util.Comparator;

public class ChunkUtility {
    public static final TicketType<ChunkPos> PISTON_BLOCK_TICKET = TicketType.create("piston_block", Comparator.comparingLong(ChunkPos::toLong), 6);
    public static final TicketType<ChunkPos> NOTE_BLOCK = TicketType.create("note_block", Comparator.comparingLong(ChunkPos::toLong), 300);
    public static final int obsidianHash = new ResourceLocation("minecraft", "obsidian").hashCode();
}
