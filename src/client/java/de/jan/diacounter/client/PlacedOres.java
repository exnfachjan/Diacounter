package de.jan.diacounter.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.core.BlockPos;

/**
 * Remembers diamond ore blocks the local player placed, so mining them again is not counted.
 * Positions are kept per server/world and dimension in config/diacounter-placed.json.
 */
public final class PlacedOres {
	private static final Gson GSON = new Gson();
	private static final Type TYPE = new TypeToken<Map<String, Set<Long>>>() { }.getType();
	private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("diacounter-placed.json");
	private static Map<String, Set<Long>> placed;

	private PlacedOres() {
	}

	private static Map<String, Set<Long>> data() {
		if (placed == null) {
			placed = new HashMap<>();
			try {
				if (Files.exists(FILE)) {
					Map<String, Set<Long>> loaded = GSON.fromJson(Files.readString(FILE), TYPE);
					if (loaded != null) {
						placed = loaded;
					}
				}
			} catch (IOException | RuntimeException e) {
				DiacounterClient.LOGGER.warn("Could not read {}", FILE, e);
			}
		}
		return placed;
	}

	private static String key(ClientLevel level) {
		Minecraft minecraft = Minecraft.getInstance();
		String world;
		if (minecraft.getSingleplayerServer() != null) {
			world = "sp:" + minecraft.getSingleplayerServer().getWorldData().getLevelName();
		} else {
			ServerData server = minecraft.getCurrentServer();
			world = "mp:" + (server != null ? server.ip : "unknown");
		}
		return world + "/" + level.dimension().identifier();
	}

	public static void markPlaced(ClientLevel level, BlockPos pos) {
		if (data().computeIfAbsent(key(level), k -> new HashSet<>()).add(pos.asLong())) {
			save();
		}
	}

	/** Returns true if the block at pos was placed by the player; the mark is removed either way. */
	public static boolean consume(ClientLevel level, BlockPos pos) {
		Set<Long> positions = data().get(key(level));
		if (positions != null && positions.remove(pos.asLong())) {
			save();
			return true;
		}
		return false;
	}

	private static void save() {
		try {
			Files.writeString(FILE, GSON.toJson(placed));
		} catch (IOException e) {
			DiacounterClient.LOGGER.warn("Could not write {}", FILE, e);
		}
	}
}
