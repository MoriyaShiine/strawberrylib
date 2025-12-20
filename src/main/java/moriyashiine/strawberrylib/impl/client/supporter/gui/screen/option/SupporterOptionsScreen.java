/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.supporter.gui.screen.option;

import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataKey;
import moriyashiine.strawberrylib.impl.client.supporter.ClientSupporterInit;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SupporterOptionsScreen extends GameOptionsScreen {
	public static final Text STRAWBERRY_TEXT = Text.literal("\uD83C\uDF53 ").formatted(Formatting.RED);
	private static final Text TITLE_TEXT = Text.translatable("options." + StrawberryLib.MOD_ID + "Title").formatted(Formatting.WHITE);

	public SupporterOptionsScreen(Screen parent, GameOptions gameOptions) {
		super(parent, gameOptions, STRAWBERRY_TEXT.copy().append(TITLE_TEXT));
	}

	@Override
	protected void addOptions() {
		List<SupporterDataKey<?>> sorted = new ArrayList<>(ClientSupporterInit.OPTIONS.keySet());
		sorted.sort(Comparator.comparing(SupporterDataKey::id));
		sorted.forEach(key -> body.addSingleOptionEntry(ClientSupporterInit.OPTIONS.get(key).option()));
	}
}
