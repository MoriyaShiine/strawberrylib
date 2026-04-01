/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.common;

import moriyashiine.strawberrylib.impl.client.payload.*;
import moriyashiine.strawberrylib.impl.common.event.ModelReplacementEvent;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrawberryLib implements ModInitializer {
	public static final String MOD_ID = "strawberrylib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static String currentModId = "";

	public static boolean scanErrorless = false;
	public static boolean bypassPvpAllowed = false;

	public static float currentAttackCooldown = -1;

	@Override
	public void onInitialize() {
		SupporterInit.init();
		initPayloads();
		initEvents();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static Identifier cid(String path) {
		return Identifier.fromNamespaceAndPath(currentModId, path);
	}

	private void initPayloads() {
		// client payloads
		PayloadTypeRegistry.clientboundPlay().register(AddAnchoredParticlePayload.TYPE, AddAnchoredParticlePayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(AddParticlesPayload.TYPE, AddParticlesPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(AddTrackingEmitterPayload.TYPE, AddTrackingEmitterPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(PlayAnchoredSoundPayload.TYPE, PlayAnchoredSoundPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(SendModelReplacementEventPayload.TYPE, SendModelReplacementEventPayload.CODEC);
	}

	private void initEvents() {
		EntitySleepEvents.ALLOW_SLEEPING.register(new ModelReplacementEvent());
	}
}
