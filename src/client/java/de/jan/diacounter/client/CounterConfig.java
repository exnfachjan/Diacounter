package de.jan.diacounter.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.BossEvent.BossBarColor;

/** Persistent settings and the running total, stored as JSON in the Fabric config directory. */
public final class CounterConfig {
	public enum Mode { HOTBAR, BOSSBAR, OFF }

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("diacounter.json");
	private static CounterConfig instance = new CounterConfig();

	public Mode mode = Mode.HOTBAR;
	public long count = 0;
	/** The boss bar is full at every multiple of this. */
	public static final int GOAL = 100;
	public BossBarColor bossColor = BossBarColor.BLUE;
	public Lang.Language language = Lang.Language.AUTO;
	public boolean showIcon = true;
	/** Extra pixels above the default hotbar-text position. */
	public int hotbarOffset = -30;

	public static CounterConfig get() {
		return instance;
	}

	public static void load() {
		try {
			if (Files.exists(FILE)) {
				CounterConfig loaded = GSON.fromJson(Files.readString(FILE), CounterConfig.class);
				if (loaded != null) {
					instance = loaded.sanitized();
				}
			}
		} catch (IOException | RuntimeException e) {
			DiacounterClient.LOGGER.warn("Could not read {}, using defaults", FILE, e);
		}
	}

	public static void save() {
		try {
			Files.writeString(FILE, GSON.toJson(instance));
		} catch (IOException e) {
			DiacounterClient.LOGGER.warn("Could not write {}", FILE, e);
		}
	}

	private CounterConfig sanitized() {
		if (mode == null) mode = Mode.HOTBAR;
		if (language == null) language = Lang.Language.AUTO;
		if (bossColor == null) bossColor = BossBarColor.BLUE;
		count = Math.max(0, count);
		return this;
	}
}
