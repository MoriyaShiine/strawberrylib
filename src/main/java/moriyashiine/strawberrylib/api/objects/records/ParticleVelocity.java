/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.objects.records;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.Vec3d;

public record ParticleVelocity(Vec3d velocity, double randomMultiplier) {
	public static final PacketCodec<ByteBuf, ParticleVelocity> PACKET_CODEC = new PacketCodec<>() {
		public ParticleVelocity decode(ByteBuf byteBuf) {
			return new ParticleVelocity(PacketByteBuf.readVec3d(byteBuf), byteBuf.readDouble());
		}

		public void encode(ByteBuf byteBuf, ParticleVelocity velocity) {
			PacketByteBuf.writeVec3d(byteBuf, velocity.velocity());
			byteBuf.writeDouble(velocity.randomMultiplier());
		}
	};

	public static final ParticleVelocity ZERO = new ParticleVelocity(Vec3d.ZERO, 0);

	public static ParticleVelocity of(Vec3d velocity, double randomMultiplier) {
		return new ParticleVelocity(velocity, randomMultiplier);
	}

	public static ParticleVelocity of(Vec3d velocity) {
		return of(velocity, 0);
	}

	public static ParticleVelocity of(double randomMultiplier) {
		return of(Vec3d.ZERO, randomMultiplier);
	}
}
