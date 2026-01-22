/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.objects.records;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

public record ParticleVelocity(Vec3 velocity, double randomMultiplier) {
	public static final StreamCodec<ByteBuf, ParticleVelocity> STREAM_CODEC = new StreamCodec<>() {
		@Override
		public void encode(ByteBuf output, ParticleVelocity value) {
			Vec3.STREAM_CODEC.encode(output, value.velocity());
			output.writeDouble(value.randomMultiplier());
		}

		@Override
		public ParticleVelocity decode(ByteBuf input) {
			return new ParticleVelocity(Vec3.STREAM_CODEC.decode(input), input.readDouble());
		}
	};

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
