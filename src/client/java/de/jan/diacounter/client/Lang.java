package de.jan.diacounter.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * Translations for the mod's own texts, independent of the game language.
 * Reads the mod's lang files directly, so the language can be chosen in the /counter menu.
 */
public final class Lang {
	public enum Language {
		AUTO(null), DE("de_de"), EN("en_us");

		final String file;

		Language(String file) {
			this.file = file;
		}
	}

	private static final Gson GSON = new Gson();
	private static final Map<Language, JsonObject> CACHE = new EnumMap<>(Language.class);

	private Lang() {
	}

	private static Language effective() {
		Language chosen = CounterConfig.get().language;
		if (chosen != Language.AUTO) {
			return chosen;
		}
		String game = Minecraft.getInstance().getLanguageManager().getSelected();
		return game != null && game.startsWith("de") ? Language.DE : Language.EN;
	}

	private static JsonObject load(Language language) {
		return CACHE.computeIfAbsent(language, l -> {
			String path = "/assets/" + DiacounterClient.MOD_ID + "/lang/" + l.file + ".json";
			try (Reader reader = new InputStreamReader(Lang.class.getResourceAsStream(path), StandardCharsets.UTF_8)) {
				return GSON.fromJson(reader, JsonObject.class);
			} catch (IOException | RuntimeException e) {
				DiacounterClient.LOGGER.warn("Could not load {}", path, e);
				return new JsonObject();
			}
		});
	}

	public static Component tr(String key, Object... args) {
		var element = load(effective()).get(key);
		String pattern = element != null ? element.getAsString() : key;
		return Component.literal(args.length == 0 ? pattern : String.format(pattern, args));
	}
}
