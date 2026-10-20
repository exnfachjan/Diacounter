package de.jan.diacounter.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.dialog.DialogConnectionAccess;
import net.minecraft.client.gui.screens.dialog.MultiButtonDialogScreen;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.ServerLinks;
import net.minecraft.server.dialog.ActionButton;
import net.minecraft.server.dialog.CommonButtonData;
import net.minecraft.server.dialog.CommonDialogData;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.server.dialog.DialogAction;
import net.minecraft.server.dialog.Input;
import net.minecraft.server.dialog.MultiActionDialog;
import net.minecraft.server.dialog.action.CustomAll;
import net.minecraft.server.dialog.body.DialogBody;
import net.minecraft.server.dialog.body.PlainMessage;
import net.minecraft.server.dialog.input.BooleanInput;
import net.minecraft.server.dialog.input.NumberRangeInput;
import net.minecraft.server.dialog.input.SingleOptionInput;
import net.minecraft.world.BossEvent.BossBarColor;
import org.jspecify.annotations.Nullable;

/**
 * The /counter menu. It uses the vanilla dialog screens (added in 1.21.6), but the dialog is built locally
 * and its custom actions are handled here instead of being sent to a server.
 */
public final class CounterMenu {
	private static final Identifier SAVE = DiacounterClient.id("save");
	private static final Identifier RESET = DiacounterClient.id("reset");

	private CounterMenu() {
	}

	public static void open(Minecraft minecraft) {
		MultiButtonDialogScreen screen = new MultiButtonDialogScreen(minecraft.gui.screen(), buildDialog(), new LocalAccess(minecraft));
		minecraft.gui.setScreen(screen);
	}

	private static MultiActionDialog buildDialog() {
		CounterConfig config = CounterConfig.get();

		List<DialogBody> body = List.of(new PlainMessage(Lang.tr("diacounter.menu.body", config.count), 200));

		List<Input> inputs = new ArrayList<>();
		inputs.add(new Input("mode", new SingleOptionInput(200, List.of(
			option("hotbar", "diacounter.menu.mode.hotbar", config.mode == CounterConfig.Mode.HOTBAR),
			option("bossbar", "diacounter.menu.mode.bossbar", config.mode == CounterConfig.Mode.BOSSBAR),
			option("off", "diacounter.menu.mode.off", config.mode == CounterConfig.Mode.OFF)
		), Lang.tr("diacounter.menu.mode"), true)));

		inputs.add(new Input("language", new SingleOptionInput(200, enumOptions(Lang.Language.values(), config.language, "diacounter.menu.language."),
			Lang.tr("diacounter.menu.language"), true)));
		inputs.add(new Input("icon", new BooleanInput(Lang.tr("diacounter.menu.icon"), config.showIcon, "true", "false")));
		inputs.add(new Input("offset", slider("diacounter.menu.offset", -30, 60, config.hotbarOffset)));
		inputs.add(new Input("color", new SingleOptionInput(200, enumOptions(BossBarColor.values(), config.bossColor, "diacounter.menu.color."),
			Lang.tr("diacounter.menu.color"), true)));

		CommonDialogData common = new CommonDialogData(
			Lang.tr("diacounter.menu.title"), Optional.empty(), true, false, DialogAction.CLOSE, body, inputs);

		List<ActionButton> actions = List.of(
			button("diacounter.menu.save", SAVE),
			button("diacounter.menu.reset", RESET)
		);
		return new MultiActionDialog(common, actions, Optional.empty(), 2);
	}

	private static NumberRangeInput slider(String key, int min, int max, int initial) {
		return new NumberRangeInput(200, Lang.tr(key), "options.generic_value",
			new NumberRangeInput.RangeInfo(min, max, Optional.of((float) initial), Optional.of(1.0F)));
	}

	private static SingleOptionInput.Entry option(String id, String translationKey, boolean initial) {
		return new SingleOptionInput.Entry(id, Optional.of(Lang.tr(translationKey)), initial);
	}

	private static <E extends Enum<E>> List<SingleOptionInput.Entry> enumOptions(E[] values, E current, String keyPrefix) {
		List<SingleOptionInput.Entry> entries = new ArrayList<>();
		for (E value : values) {
			entries.add(option(value.name(), keyPrefix + value.name().toLowerCase(), value == current));
		}
		return entries;
	}

	private static ActionButton button(String labelKey, Identifier action) {
		return new ActionButton(new CommonButtonData(Lang.tr(labelKey), 150), Optional.of(new CustomAll(action, Optional.empty())));
	}

	private static void handleAction(Identifier id, Optional<Tag> payload) {
		CounterConfig config = CounterConfig.get();
		if (id.equals(RESET)) {
			config.count = 0;
		} else if (id.equals(SAVE) && payload.orElse(null) instanceof CompoundTag tag) {
			config.mode = switch (tag.getStringOr("mode", "hotbar")) {
				case "bossbar" -> CounterConfig.Mode.BOSSBAR;
				case "off" -> CounterConfig.Mode.OFF;
				default -> CounterConfig.Mode.HOTBAR;
			};
			config.language = parse(Lang.Language.values(), tag.getStringOr("language", ""), config.language);
			config.showIcon = tag.getBooleanOr("icon", config.showIcon);
			config.hotbarOffset = Math.round(tag.getFloatOr("offset", config.hotbarOffset));
			config.bossColor = parse(BossBarColor.values(), tag.getStringOr("color", ""), config.bossColor);
		}
		CounterConfig.save();
	}

	private static <E extends Enum<E>> E parse(E[] values, String name, E fallback) {
		for (E value : values) {
			if (value.name().equals(name)) {
				return value;
			}
		}
		return fallback;
	}

	/** Connection access for a dialog that never talks to a server. */
	private record LocalAccess(Minecraft minecraft) implements DialogConnectionAccess {
		@Override
		public void disconnect(Component message) {
			minecraft.disconnectFromWorld(message);
		}

		@Override
		public void runCommand(String command, @Nullable Screen activeScreen) {
			minecraft.gui.setScreen(activeScreen);
		}

		@Override
		public void openDialog(Holder<Dialog> dialog, @Nullable Screen activeScreen) {
			minecraft.gui.setScreen(activeScreen);
		}

		@Override
		public void sendCustomAction(Identifier id, Optional<Tag> payload) {
			handleAction(id, payload);
		}

		@Override
		public ServerLinks serverLinks() {
			return ServerLinks.EMPTY;
		}
	}
}
