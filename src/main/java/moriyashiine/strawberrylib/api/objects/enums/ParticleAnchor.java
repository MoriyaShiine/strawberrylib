/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.objects.enums;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;

import java.util.function.IntFunction;

public enum ParticleAnchor {
	BASE, BODY, EYES, CHEST, FEET;

	private static final IntFunction<ParticleAnchor> BY_ID = ByIdMap.continuous(ParticleAnchor::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final StreamCodec<ByteBuf, ParticleAnchor> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, ParticleAnchor::ordinal);
}
