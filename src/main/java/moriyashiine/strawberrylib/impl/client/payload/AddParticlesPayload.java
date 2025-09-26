/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.payload;

import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import moriyashiine.strawberrylib.api.objects.records.ParticleVelocity;
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

public record AddParticlesPayload(int entityId, ParticleType<?> particleType, int count,
								  ParticleAnchor anchor, ParticleVelocity velocity) implements CustomPayload {
	public static final Id<AddParticlesPayload> ID = new Id<>(StrawberryLib.id("add_particles"));
	public static final PacketCodec<RegistryByteBuf, AddParticlesPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, AddParticlesPayload::entityId,
			PacketCodecs.registryCodec(Registries.PARTICLE_TYPE.getCodec()), AddParticlesPayload::particleType,
			PacketCodecs.VAR_INT, AddParticlesPayload::count,
			ParticleAnchor.PACKET_CODEC, AddParticlesPayload::anchor,
			ParticleVelocity.PACKET_CODEC, AddParticlesPayload::velocity,
			AddParticlesPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(ServerPlayerEntity receiver, Entity entity, ParticleType<?> particleType, int count, ParticleAnchor anchor, ParticleVelocity velocity) {
		ServerPlayNetworking.send(receiver, new AddParticlesPayload(entity.getId(), particleType, count, anchor, velocity));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<AddParticlesPayload> {
		@Override
		public void receive(AddParticlesPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().getEntityWorld().getEntityById(payload.entityId());
			if (entity != null) {
				SLibClientUtils.addParticles(entity, payload.particleType(), payload.count, payload.anchor(), payload.velocity());
			}
		}
	}
}
