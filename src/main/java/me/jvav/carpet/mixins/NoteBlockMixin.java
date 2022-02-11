package me.jvav.carpet.mixins;

import me.jvav.carpet.CarpetHuajiMURServerSettings;
import me.jvav.carpet.ChunkUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin
{
    @Inject(at = @At("HEAD"), method = "playNote")
    private void loadChunk(Level level, BlockPos pos, CallbackInfo ci)
    {
        if (!CarpetHuajiMURServerSettings.noteBlockChunkLoader) {
            return;
        }
        ChunkPos cp = new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4);
        ((ServerLevel) level).getChunkSource().addRegionTicket(ChunkUtility.NOTE_BLOCK, cp, 3, cp);
    }
}
