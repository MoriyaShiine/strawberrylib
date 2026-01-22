/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.client.supporter;

import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataKey;
import moriyashiine.strawberrylib.api.supporter.objects.client.ClientSupporterData;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.impl.common.supporter.payload.SyncGlintColorPayload;
import net.minecraft.client.OptionInstance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClientSupporterInit {
	public static final Map<SupporterDataKey<?>, ClientSupporterData<?>> OPTIONS = new HashMap<>();

	private static final OptionInstance.Enum<GlintColor> GLINT_COLOR_VALUES = new OptionInstance.Enum<>(
			Arrays.stream(GlintColor.values()).toList(),
			GlintColor.CODEC);

	public static void init() {
		SLibSupporterUtils.registerOption(SupporterInit.EQUIPPABLE_GLINT_COLOR,
				(_, value) -> value.getOptionsName(), GLINT_COLOR_VALUES, GlintColor.PURPLE,
				GlintColor::valueOf, GlintColor::name, color -> SyncGlintColorPayload.send(true, color));
		SLibSupporterUtils.registerOption(SupporterInit.GLINT_COLOR,
				SLibSupporterUtils.createOption(SupporterInit.GLINT_COLOR, (_, value) -> value.getOptionsName(), GLINT_COLOR_VALUES, GlintColor.PURPLE),
				GlintColor::valueOf, GlintColor::name, color -> SyncGlintColorPayload.send(false, color));
	}
}
