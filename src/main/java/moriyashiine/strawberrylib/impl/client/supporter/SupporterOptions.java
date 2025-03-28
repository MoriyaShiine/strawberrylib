/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.supporter;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.client.option.SimpleOption;

import java.util.Arrays;

public class SupporterOptions {
	private static final SimpleOption.PotentialValuesBasedCallbacks<GlintColor> GLINT_COLOR_VALUES = new SimpleOption.PotentialValuesBasedCallbacks<>(
			Arrays.stream(GlintColor.values()).toList(),
			GlintColor.CODEC);

	public static final SimpleOption<GlintColor> EQUIPPABLE_GLINT_COLOR = new SimpleOption<>("options." + StrawberryLib.MOD_ID + ".equippableGlintColor", SimpleOption.emptyTooltip(), (optionText, value) -> value.getOptionsName(), GLINT_COLOR_VALUES, GlintColor.PURPLE, value -> {
	});
	public static final SimpleOption<GlintColor> GLINT_COLOR = new SimpleOption<>("options." + StrawberryLib.MOD_ID + ".glintColor", SimpleOption.emptyTooltip(), (optionText, value) -> value.getOptionsName(), GLINT_COLOR_VALUES, GlintColor.PURPLE, value -> {
	});
}
