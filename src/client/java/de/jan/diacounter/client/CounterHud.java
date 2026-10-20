package de.jan.diacounter.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.objects.AtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class CounterHud {
	private static final ItemStack ICON = new ItemStack(Items.DIAMOND_ORE);

	private CounterHud() {
	}

	private static final Component BAR_ICON = Component.object(new AtlasSprite(AtlasSprite.DEFAULT_ATLAS, Identifier.withDefaultNamespace("block/diamond_ore")));

	public static Component label() {
		return Lang.tr("diacounter.hud.label", CounterConfig.get().count);
	}

	/** Boss bar title: the label, with a diamond ore sprite in front if enabled. */
	public static Component barLabel() {
		return CounterConfig.get().showIcon ? Component.empty().append(BAR_ICON).append(" ").append(label()) : label();
	}

	public static void extractHotbarCounter(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		CounterConfig config = CounterConfig.get();
		Minecraft minecraft = Minecraft.getInstance();
		if (config.mode != CounterConfig.Mode.HOTBAR) {
			return;
		}
		Component text = label();
		int textWidth = minecraft.font.width(text);
		int iconWidth = config.showIcon ? 18 : 0;
		int x = (graphics.guiWidth() - textWidth - iconWidth) / 2;
		// Just above the held-item name.
		int y = graphics.guiHeight() - 69;
		if (config.showIcon) {
			graphics.item(ICON, x, y - 4);
			x += iconWidth;
		}
		graphics.text(minecraft.font, text, x, y, 0xFF55FFFF, true);
	}
}
