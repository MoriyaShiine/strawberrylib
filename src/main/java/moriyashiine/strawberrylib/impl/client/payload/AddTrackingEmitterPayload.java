/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.client.payload;

import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public record AddTrackingEmitterPayload(int entityId, ParticleType<?> particle) implements CustomPacketPayload {
	public static final Type<AddTrackingEmitterPayload> TYPE = new Type<>(StrawberryLib.id("add_tracking_emitter"));
	public static final StreamCodec<RegistryFriendlyByteBuf, AddTrackingEmitterPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, AddTrackingEmitterPayload::entityId,
			ByteBufCodecs.fromCodecWithRegistries(BuiltInRegistries.PARTICLE_TYPE.byNameCodec()), AddTrackingEmitterPayload::particle,
			AddTrackingEmitterPayload::new);

	@Override
	public Type<AddTrackingEmitterPayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer receiver, Entity entity, ParticleType<?> particle) {
		ServerPlayNetworking.send(receiver, new AddTrackingEmitterPayload(entity.getId(), particle));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<AddTrackingEmitterPayload> {
		@Override
		public void receive(AddTrackingEmitterPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().level().getEntity(payload.entityId());
			if (entity != null) {
				SLibClientUtils.addTrackingEmitter(entity, payload.particle());
			}
		}
	}
}