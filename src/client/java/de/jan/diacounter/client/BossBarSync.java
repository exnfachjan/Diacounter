package de.jan.diacounter.client;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.world.BossEvent.BossBarOverlay;

/** Keeps one client-only boss bar in the vanilla boss overlay in sync with the counter. */
public final class BossBarSync {
	private static final UUID ID = UUID.nameUUIDFromBytes("diacounter:bossbar".getBytes(StandardCharsets.UTF_8));

	private BossBarSync() {
	}

	public static void sync(Map<UUID, LerpingBossEvent> events) {
		CounterConfig config = CounterConfig.get();
		if (config.mode != CounterConfig.Mode.BOSSBAR) {
			events.remove(ID);
			return;
		}
		// Full exactly at goal, 2*goal, ...; then starts over (101 -> 1%).
		float progress = config.count <= 0 ? 0.0F : (float) ((config.count - 1) % CounterConfig.GOAL + 1) / CounterConfig.GOAL;
		LerpingBossEvent event = events.get(ID);
		if (event == null) {
			event = new LerpingBossEvent(ID, CounterHud.barLabel(), progress, config.bossColor, BossBarOverlay.PROGRESS, false, false, false);
			events.put(ID, event);
			return;
		}
		event.setName(CounterHud.barLabel());
		event.setColor(config.bossColor);
		event.setOverlay(BossBarOverlay.PROGRESS);
		if (Math.abs(event.getProgress() - progress) > 0.0001F) {
			event.setProgress(progress);
		}
	}
}
