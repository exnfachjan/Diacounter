package de.jan.diacounter.client.mixin;

import de.jan.diacounter.client.DiacounterClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@Unique
	private boolean diacounter$wasDiamondOre;

	@Inject(method = "destroyBlock", at = @At("HEAD"))
	private void diacounter$rememberBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		this.diacounter$wasDiamondOre = this.minecraft.level != null
			&& DiacounterClient.isDiamondOre(this.minecraft.level.getBlockState(pos).getBlock());
	}

	@Inject(method = "destroyBlock", at = @At("RETURN"))
	private void diacounter$countBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (this.diacounter$wasDiamondOre && cir.getReturnValueZ()) {
			DiacounterClient.onDiamondOreMined();
		}
		this.diacounter$wasDiamondOre = false;
	}
}
