/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.objects.enums;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum ParticleAnchor {
	BASE, BODY, EYES, CHEST, FEET;

	private static final IntFunction<ParticleAnchor> INDEX_MAPPER = ValueLists.createIndexToValueFunction(ParticleAnchor::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);
	public static final PacketCodec<ByteBuf, ParticleAnchor> PACKET_CODEC = PacketCodecs.indexed(INDEX_MAPPER, ParticleAnchor::ordinal);
}
