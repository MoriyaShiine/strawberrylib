/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.client.supporter.gui.screen.option;

import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataKey;
import moriyashiine.strawberrylib.impl.client.supporter.ClientSupporterInit;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SupporterOptionsScreen extends OptionsSubScreen {
	public static final Component STRAWBERRY_TEXT = Component.literal("\uD83C\uDF53 ").withStyle(ChatFormatting.RED);
	private static final Component TITLE_TEXT = Component.translatable("options." + StrawberryLib.MOD_ID + "Title").withStyle(ChatFormatting.WHITE);

	public SupporterOptionsScreen(Screen parent, Options gameOptions) {
		super(parent, gameOptions, STRAWBERRY_TEXT.copy().append(TITLE_TEXT));
	}

	@Override
	protected void addOptions() {
		List<SupporterDataKey<?>> sorted = new ArrayList<>(ClientSupporterInit.OPTIONS.keySet());
		sorted.sort(Comparator.comparing(SupporterDataKey::id));
		sorted.forEach(key -> list.addBig(ClientSupporterInit.OPTIONS.get(key).option()));
	}
}
