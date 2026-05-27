/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.objects.records;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

public record ParticleVelocity(Vec3 velocity, double randomMultiplier) {
	public static final StreamCodec<FriendlyByteBuf, ParticleVelocity> STREAM_CODEC = StreamCodec.composite(
			Vec3.STREAM_CODEC, ParticleVelocity::velocity,
			ByteBufCodecs.DOUBLE, ParticleVelocity::randomMultiplier,
			ParticleVelocity::new
	);

	public static final ParticleVelocity ZERO = new ParticleVelocity(Vec3.ZERO, 0);

	public static ParticleVelocity of(Vec3 velocity, double randomMultiplier) {
		return new ParticleVelocity(velocity, randomMultiplier);
	}

	public static ParticleVelocity of(Vec3 velocity) {
		return of(velocity, 0);
	}

	public static ParticleVelocity of(double randomMultiplier) {
		return of(Vec3.ZERO, randomMultiplier);
	}
}
