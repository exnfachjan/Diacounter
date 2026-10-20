package de.jan.diacounter.client.mixin;

import de.jan.diacounter.client.DiacounterClient;
import de.jan.diacounter.client.PlacedOres;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {
	@Inject(method = "place", at = @At("RETURN"))
	private void diacounter$markPlacedOre(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {
		BlockItem self = (BlockItem) (Object) this;
		if (context.getLevel() instanceof ClientLevel level
			&& context.getPlayer() == Minecraft.getInstance().player
			&& cir.getReturnValue().consumesAction()
			&& DiacounterClient.isDiamondOre(self.getBlock())) {
			PlacedOres.markPlaced(level, context.getClickedPos());
		}
	}
}
