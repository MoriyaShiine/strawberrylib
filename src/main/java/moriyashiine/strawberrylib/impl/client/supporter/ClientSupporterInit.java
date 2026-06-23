/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.client.supporter;

import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataType;
import moriyashiine.strawberrylib.api.supporter.objects.client.ClientSupporterData;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.minecraft.client.OptionInstance;

import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;

public class ClientSupporterInit {
	public static final Map<SupporterDataType<?>, ClientSupporterData<?>> OPTIONS = new IdentityHashMap<>();

	private static final OptionInstance.Enum<GlintColor> GLINT_COLOR_VALUES = new OptionInstance.Enum<>(
			Arrays.stream(GlintColor.values()).toList(),
			GlintColor.CODEC);

	public static void init() {
		SLibSupporterUtils.registerOption(SupporterInit.EQUIPPABLE_GLINT_COLOR,
				(_, value) -> value.getOptionsName(), GLINT_COLOR_VALUES, GlintColor.PURPLE);
		SLibSupporterUtils.registerOption(SupporterInit.GLINT_COLOR,
				(_, value) -> value.getOptionsName(), GLINT_COLOR_VALUES, GlintColor.PURPLE);
	}
}
