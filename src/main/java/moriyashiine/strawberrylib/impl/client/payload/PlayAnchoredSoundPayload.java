/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.payload;

import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;

public record PlayAnchoredSoundPayload(int entityId, SoundEvent soundEvent) implements CustomPayload {
	public static final Id<PlayAnchoredSoundPayload> ID = new Id<>(StrawberryLib.id("play_anchored_sound"));
	public static final PacketCodec<PacketByteBuf, PlayAnchoredSoundPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, PlayAnchoredSoundPayload::entityId,
			SoundEvent.PACKET_CODEC, PlayAnchoredSoundPayload::soundEvent,
			PlayAnchoredSoundPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(ServerPlayerEntity receiver, Entity entity, SoundEvent soundEvent) {
		ServerPlayNetworking.send(receiver, new PlayAnchoredSoundPayload(entity.getId(), soundEvent));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<PlayAnchoredSoundPayload> {
		@Override
		public void receive(PlayAnchoredSoundPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().getEntityWorld().getEntityById(payload.entityId());
			if (entity != null) {
				SLibClientUtils.playAnchoredSound(entity, payload.soundEvent());
			}
		}
	}
}
