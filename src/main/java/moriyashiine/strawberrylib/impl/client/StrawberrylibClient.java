/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client;

import moriyashiine.strawberrylib.impl.client.payload.AddEmitterParticlePayload;
import moriyashiine.strawberrylib.impl.client.payload.AddParticlesPayload;
import moriyashiine.strawberrylib.impl.client.payload.PlayAnchoredSoundPayload;
import moriyashiine.strawberrylib.impl.client.payload.SendModelReplacementStatusPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class StrawberrylibClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		initPayloads();
	}

	private void initPayloads() {
		ClientPlayNetworking.registerGlobalReceiver(AddEmitterParticlePayload.ID, new AddEmitterParticlePayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(AddParticlesPayload.ID, new AddParticlesPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(PlayAnchoredSoundPayload.ID, new PlayAnchoredSoundPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(SendModelReplacementStatusPayload.ID, new SendModelReplacementStatusPayload.Receiver());
	}
}
