/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common.supporter;

import dev.upcraft.datasync.api.util.Entitlements;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.supporter.payload.SyncGlintColorPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class SupporterInit {
	private static final Identifier SUPPORTER_KEY = StrawberryLib.id("supporter");

	public static void init() {
		initPayloads();
	}

	public static boolean isSupporter(PlayerEntity player) {
		return isSupporter(player.getUuid());
	}

	public static boolean isSupporter(UUID uuid) {
		return FabricLoader.getInstance().isDevelopmentEnvironment() || Entitlements.getOrEmpty(uuid).keys().contains(SUPPORTER_KEY);
	}

	private static void initPayloads() {
		// server payloads
		PayloadTypeRegistry.playC2S().register(SyncGlintColorPayload.ID, SyncGlintColorPayload.CODEC);
		// server receivers
		ServerPlayNetworking.registerGlobalReceiver(SyncGlintColorPayload.ID, new SyncGlintColorPayload.Receiver());
	}
}
