/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.common.supporter;

import dev.upcraft.datasync.api.util.Entitlements;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterData;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataKey;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.supporter.payload.SyncGlintColorPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SupporterInit {
	private static final Identifier SUPPORTER_KEY = StrawberryLib.id("supporter");

	public static final Map<SupporterDataKey<?>, SupporterData<?>> DATA = new HashMap<>();

	public static final SupporterDataKey<GlintColor> EQUIPPABLE_GLINT_COLOR = SLibSupporterUtils.registerData(StrawberryLib.id("equippable_glint_color"), GlintColor.CODEC, GlintColor.PURPLE);
	public static final SupporterDataKey<GlintColor> GLINT_COLOR = SLibSupporterUtils.registerData(StrawberryLib.id("glint_color"), GlintColor.CODEC, GlintColor.PURPLE);

	public static void init() {
		initPayloads();
	}

	public static boolean isSupporter(Player player) {
		return isSupporter(player.getUUID());
	}

	public static boolean isSupporter(UUID uuid) {
		return FabricLoader.getInstance().isDevelopmentEnvironment() || Entitlements.getOrEmpty(uuid).keys().contains(SUPPORTER_KEY);
	}

	private static void initPayloads() {
		// server payloads
		PayloadTypeRegistry.serverboundPlay().register(SyncGlintColorPayload.TYPE, SyncGlintColorPayload.CODEC);
		// server receivers
		ServerPlayNetworking.registerGlobalReceiver(SyncGlintColorPayload.TYPE, new SyncGlintColorPayload.Receiver());
	}
}
