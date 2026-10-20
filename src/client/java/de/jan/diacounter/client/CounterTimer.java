package de.jan.diacounter.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

/** Countdown shown next to the counter. Runs on client ticks, so it stands still while the game is paused. */
public final class CounterTimer {
	public enum State { IDLE, RUNNING, PAUSED, FINISHED }

	private static State state = State.IDLE;
	private static int remainingTicks;

	private CounterTimer() {
	}

	public static State state() {
		return state;
	}

	/** Starts (or restarts) the countdown with the length from the menu. */
	public static void start() {
		remainingTicks = CounterConfig.get().timerMinutes * 60 * 20;
		state = State.RUNNING;
	}

	/** Diamonds are only counted until a timer has run out; /counter start or resetting the counter unlocks it again. */
	public static boolean countingAllowed() {
		return state != State.FINISHED;
	}

	public static void reset() {
		state = State.IDLE;
	}

	public static boolean pause() {
		if (state != State.RUNNING) {
			return false;
		}
		state = State.PAUSED;
		return true;
	}

	public static boolean resume() {
		if (state != State.PAUSED) {
			return false;
		}
		state = State.RUNNING;
		return true;
	}

	public static void tick(Minecraft minecraft) {
		if (state != State.RUNNING || minecraft.player == null || minecraft.isPaused()) {
			return;
		}
		if (--remainingTicks <= 0) {
			state = State.FINISHED;
			minecraft.player.sendSystemMessage(Lang.tr("diacounter.timer.finished", CounterConfig.get().count));
			minecraft.player.playSound(SoundEvents.PLAYER_LEVELUP);
		}
	}

	/** Remaining time as m:ss or h:mm:ss, or null when no timer is active. */
	public static String formatted() {
		if (state == State.IDLE) {
			return null;
		}
		int seconds = state == State.FINISHED ? 0 : (remainingTicks + 19) / 20;
		int h = seconds / 3600;
		int m = seconds / 60 % 60;
		int s = seconds % 60;
		return h > 0 ? String.format("%d:%02d:%02d", h, m, s) : String.format("%d:%02d", m, s);
	}

	public static Component message(String key, Object... args) {
		return Lang.tr(key, args);
	}
}
