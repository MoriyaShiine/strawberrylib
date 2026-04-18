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

public record AddAnchoredParticlePayload(int entityId, ParticleType<?> particle, double yOffset, double speed, double intensity) implements CustomPacketPayload {
	public static final Type<AddAnchoredParticlePayload> TYPE = new Type<>(StrawberryLib.id("add_anchored_particle"));
	public static final StreamCodec<RegistryFriendlyByteBuf, AddAnchoredParticlePayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, AddAnchoredParticlePayload::entityId,
			ByteBufCodecs.fromCodecWithRegistries(BuiltInRegistries.PARTICLE_TYPE.byNameCodec()), AddAnchoredParticlePayload::particle,
			ByteBufCodecs.DOUBLE, AddAnchoredParticlePayload::yOffset,
			ByteBufCodecs.DOUBLE, AddAnchoredParticlePayload::speed,
			ByteBufCodecs.DOUBLE, AddAnchoredParticlePayload::intensity,
			AddAnchoredParticlePayload::new);

	@Override
	public Type<AddAnchoredParticlePayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer receiver, Entity entity, ParticleType<?> particle, double yOffset, double speed, double intensity) {
		ServerPlayNetworking.send(receiver, new AddAnchoredParticlePayload(entity.getId(), particle, yOffset, speed, intensity));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<AddAnchoredParticlePayload> {
		@Override
		public void receive(AddAnchoredParticlePayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().level().getEntity(payload.entityId());
			if (entity != null) {
				SLibClientUtils.addAnchoredParticle(entity, payload.particle(), payload.yOffset(), payload.speed(), payload.intensity());
			}
		}
	}
}