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
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public record AddParticlesPayload(int entityId, ParticleType<?> particle, int count,
								  ParticleAnchor anchor, ParticleVelocity velocity) implements CustomPacketPayload {
	public static final Type<AddParticlesPayload> TYPE = new Type<>(StrawberryLib.id("add_particles"));
	public static final StreamCodec<RegistryFriendlyByteBuf, AddParticlesPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, AddParticlesPayload::entityId,
			ByteBufCodecs.fromCodecWithRegistries(BuiltInRegistries.PARTICLE_TYPE.byNameCodec()), AddParticlesPayload::particle,
			ByteBufCodecs.VAR_INT, AddParticlesPayload::count,
			ParticleAnchor.STREAM_CODEC, AddParticlesPayload::anchor,
			ParticleVelocity.STREAM_CODEC, AddParticlesPayload::velocity,
			AddParticlesPayload::new);

	@Override
	public Type<AddParticlesPayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer receiver, Entity entity, ParticleType<?> particle, int count, ParticleAnchor anchor, ParticleVelocity velocity) {
		ServerPlayNetworking.send(receiver, new AddParticlesPayload(entity.getId(), particle, count, anchor, velocity));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<AddParticlesPayload> {
		@Override
		public void receive(AddParticlesPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().level().getEntity(payload.entityId());
			if (entity != null) {
				SLibClientUtils.addParticles(entity, payload.particle(), payload.count(), payload.anchor(), payload.velocity());
			}
		}
	}
}
