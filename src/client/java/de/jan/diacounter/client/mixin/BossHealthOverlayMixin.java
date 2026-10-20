package de.jan.diacounter.client.mixin;

import de.jan.diacounter.client.BossBarSync;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin {
	@Shadow
	@Final
	private Map<UUID, LerpingBossEvent> events;

	@Inject(method = "extractRenderState", at = @At("HEAD"))
	private void diacounter$syncBar(GuiGraphicsExtractor graphics, CallbackInfo ci) {
		BossBarSync.sync(this.events);
	}
}
