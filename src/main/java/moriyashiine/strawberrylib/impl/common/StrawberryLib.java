/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common;

import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import moriyashiine.strawberrylib.impl.client.payload.AddEmitterParticlePayload;
import moriyashiine.strawberrylib.impl.client.payload.AddParticlesPayload;
import moriyashiine.strawberrylib.impl.client.payload.PlayAnchoredSoundPayload;
import moriyashiine.strawberrylib.impl.client.payload.SendModelReplacementStatusPayload;
import moriyashiine.strawberrylib.impl.common.event.ModelReplacementEvent;
import moriyashiine.strawberrylib.impl.common.event.PreventsEquipmentUsageEvent;
import moriyashiine.strawberrylib.impl.common.event.TickEntitiesEvent;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrawberryLib implements ModInitializer {
	public static final String MOD_ID = "strawberrylib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static String currentModId = "";

	@Override
	public void onInitialize() {
		SupporterInit.init();
		initPayloads();
		initEvents();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static Identifier cid(String path) {
		return Identifier.of(currentModId, path);
	}

	private void initPayloads() {
		// client payloads
		PayloadTypeRegistry.playS2C().register(AddEmitterParticlePayload.ID, AddEmitterParticlePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(AddParticlesPayload.ID, AddParticlesPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayAnchoredSoundPayload.ID, PlayAnchoredSoundPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SendModelReplacementStatusPayload.ID, SendModelReplacementStatusPayload.CODEC);
	}

	private void initEvents() {
		EntitySleepEvents.ALLOW_SLEEPING.register(new ModelReplacementEvent());
		TickEntityEvent.EVENT.register(new PreventsEquipmentUsageEvent());
		ServerTickEvents.END_WORLD_TICK.register(new TickEntitiesEvent());
	}
}
