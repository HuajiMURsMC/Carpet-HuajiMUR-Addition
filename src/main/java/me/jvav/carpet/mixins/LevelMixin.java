package me.jvav.carpet.mixins;

import me.jvav.carpet.CarpetHuajiMURServerSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin {
    @Shadow public abstract boolean isLoaded(BlockPos blockPos);

    @Inject(method = "getBlockState", at = @At("HEAD"), cancellable = true)
    private void onSendParticles(BlockPos blockPos, CallbackInfoReturnable<BlockState> cir) {
        if (!CarpetHuajiMURServerSettings.antiNoCom) {
            return;
        }
        if (!this.isLoaded(blockPos)) {
            cir.setReturnValue(Blocks.VOID_AIR.defaultBlockState());
        }
    }
}
