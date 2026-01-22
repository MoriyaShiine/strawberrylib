/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.client;

import moriyashiine.strawberrylib.impl.client.payload.*;
import moriyashiine.strawberrylib.impl.client.supporter.ClientSupporterInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

public class StrawberrylibClient implements ClientModInitializer {
	public static AvatarRenderState currentAvatarRenderState = null;

	@Override
	public void onInitializeClient() {
		ClientSupporterInit.init();
		initPayloads();
	}

	private void initPayloads() {
		ClientPlayNetworking.registerGlobalReceiver(AddAnchoredParticlePayload.TYPE, new AddAnchoredParticlePayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(AddParticlesPayload.TYPE, new AddParticlesPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(AddTrackingEmitterPayload.TYPE, new AddTrackingEmitterPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(PlayAnchoredSoundPayload.TYPE, new PlayAnchoredSoundPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(SendModelReplacementEventPayload.TYPE, new SendModelReplacementEventPayload.Receiver());
	}
}
