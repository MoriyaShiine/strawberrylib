/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client;

import moriyashiine.strawberrylib.impl.client.payload.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;

public class StrawberrylibClient implements ClientModInitializer {
	public static PlayerEntityRenderState currentPlayerRenderState = null;

	@Override
	public void onInitializeClient() {
		initPayloads();
	}

	private void initPayloads() {
		ClientPlayNetworking.registerGlobalReceiver(AddAnchoredParticlePayload.ID, new AddAnchoredParticlePayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(AddEmitterParticlePayload.ID, new AddEmitterParticlePayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(AddParticlesPayload.ID, new AddParticlesPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(PlayAnchoredSoundPayload.ID, new PlayAnchoredSoundPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(SendModelReplacementStatusPayload.ID, new SendModelReplacementStatusPayload.Receiver());
	}
}
