package de.jan.diacounter.client;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiacounterClient implements ClientModInitializer {
	public static final String MOD_ID = "diacounter";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static boolean openMenuNextTick;

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static boolean isDiamondOre(Block block) {
		return block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE;
	}

	@Override
	public void onInitializeClient() {
		CounterConfig.load();

		// Client-side command: never sent to the server, so nothing has to be installed there.
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, buildContext) ->
			dispatcher.register(ClientCommands.literal("counter").executes(context -> {
				// The chat screen closes right after the command runs, so open the menu on the next tick.
				openMenuNextTick = true;
				return Command.SINGLE_SUCCESS;
			})));

		ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
			if (openMenuNextTick) {
				openMenuNextTick = false;
				CounterMenu.open(minecraft);
			}
		});

		HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, id("hotbar_counter"), CounterHud::extractHotbarCounter);

		ClientLifecycleEvents.CLIENT_STOPPING.register(minecraft -> CounterConfig.save());
	}

	/** Called by the block-break mixin for every diamond ore the local player breaks. */
	public static void onDiamondOreMined() {
		CounterConfig.get().count++;
		CounterConfig.save();
	}
}
