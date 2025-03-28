/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.supporter.gui.screen.option;

import moriyashiine.strawberrylib.impl.client.supporter.SupporterOptions;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SupporterOptionsScreen extends GameOptionsScreen {
	public static final MutableText STRAWBERRY_TEXT = Text.literal("\uD83C\uDF53 ").formatted(Formatting.RED);
	private static final Text TITLE_TEXT = Text.translatable("options." + StrawberryLib.MOD_ID + "Title").formatted(Formatting.WHITE);

	public SupporterOptionsScreen(Screen parent, GameOptions gameOptions) {
		super(parent, gameOptions, STRAWBERRY_TEXT.copy().append(TITLE_TEXT));
	}

	@Override
	protected void addOptions() {
		body.addSingleOptionEntry(SupporterOptions.EQUIPPABLE_GLINT_COLOR);
		body.addSingleOptionEntry(SupporterOptions.GLINT_COLOR);
	}
}
