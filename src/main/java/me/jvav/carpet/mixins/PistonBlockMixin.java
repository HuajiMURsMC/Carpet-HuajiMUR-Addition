package me.jvav.carpet.mixins;

import me.jvav.carpet.CarpetHuajiMURServerSettings;
import me.jvav.carpet.ChunkUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PistonBaseBlock.class)
public abstract class PistonBlockMixin
{
    @Inject(method = "triggerEvent", at = @At("HEAD"))
    private void load(BlockState state, Level level, BlockPos pos, int i, int j, CallbackInfoReturnable<Boolean> cir)
    {
        if (!CarpetHuajiMURServerSettings.pistonBlockChunkLoader) {
            return;
        }
        if(level instanceof ServerLevel)
        {
            Direction direction = state.getValue(DirectionalBlock.FACING);

            BlockPos nbp = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
            Block block = level.getBlockState(nbp).getBlock();

            if (((Integer) Registry.BLOCK.getId(block)).hashCode() == ChunkUtility.obsidianHash)
            {
                int x = pos.getX() + direction.getStepX();
                int z = pos.getZ() + direction.getStepZ();

                ChunkPos cp = new ChunkPos(x >> 4, z >> 4);
                ((ServerLevel) level).getChunkSource().addRegionTicket(ChunkUtility.PISTON_BLOCK_TICKET, cp, 1, cp);
            }
        }
    }
}