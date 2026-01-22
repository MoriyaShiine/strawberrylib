/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.client.payload;

import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;

public record PlayAnchoredSoundPayload(int entityId, SoundEvent sound) implements CustomPacketPayload {
	public static final Type<PlayAnchoredSoundPayload> TYPE = new Type<>(StrawberryLib.id("play_anchored_sound"));
	public static final StreamCodec<FriendlyByteBuf, PlayAnchoredSoundPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, PlayAnchoredSoundPayload::entityId,
			SoundEvent.DIRECT_STREAM_CODEC, PlayAnchoredSoundPayload::sound,
			PlayAnchoredSoundPayload::new);

	@Override
	public Type<PlayAnchoredSoundPayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer receiver, Entity entity, SoundEvent sound) {
		ServerPlayNetworking.send(receiver, new PlayAnchoredSoundPayload(entity.getId(), sound));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<PlayAnchoredSoundPayload> {
		@Override
		public void receive(PlayAnchoredSoundPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().level().getEntity(payload.entityId());
			if (entity != null) {
				SLibClientUtils.playAnchoredSound(entity, payload.sound());
			}
		}
	}
}
