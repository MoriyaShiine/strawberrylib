/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.payload;

import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;

public record AddAnchoredParticlePayload(int entityId, ParticleType<?> particleType, double yOffset, double speed,
										 double intensity) implements CustomPayload {
	public static final Id<AddAnchoredParticlePayload> ID = new Id<>(StrawberryLib.id("add_anchored_particle"));
	public static final PacketCodec<RegistryByteBuf, AddAnchoredParticlePayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, AddAnchoredParticlePayload::entityId,
			PacketCodecs.registryCodec(Registries.PARTICLE_TYPE.getCodec()), AddAnchoredParticlePayload::particleType,
			PacketCodecs.DOUBLE, AddAnchoredParticlePayload::yOffset,
			PacketCodecs.DOUBLE, AddAnchoredParticlePayload::speed,
			PacketCodecs.DOUBLE, AddAnchoredParticlePayload::intensity,
			AddAnchoredParticlePayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(ServerPlayerEntity receiver, Entity entity, ParticleType<?> particleType, double yOffset, double speed, double intensity) {
		ServerPlayNetworking.send(receiver, new AddAnchoredParticlePayload(entity.getId(), particleType, yOffset, speed, intensity));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<AddAnchoredParticlePayload> {
		@Override
		public void receive(AddAnchoredParticlePayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().getEntityWorld().getEntityById(payload.entityId());
			if (entity != null) {
				SLibClientUtils.addAnchoredParticle(entity, payload.particleType(), payload.yOffset(), payload.speed(), payload.intensity());
			}
		}
	}
}