/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.supporter.objects;

import com.mojang.serialization.Codec;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

@SuppressWarnings("ClassCanBeRecord") // prevents serialization errors
public class SupporterDataType<T> {
	public static final Codec<SupporterDataType<?>> CODEC = Codec.lazyInitialized(SupporterInit.SUPPORTER_DATA_TYPE::byNameCodec);
	public static final StreamCodec<RegistryFriendlyByteBuf, SupporterDataType<?>> STREAM_CODEC = ByteBufCodecs.registry(SupporterInit.SUPPORTER_DATA_TYPE_KEY);

	private final Codec<T> codec;
	private final StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec;
	private final T initialValue;

	public SupporterDataType(Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, T initialValue) {
		this.codec = codec;
		this.streamCodec = streamCodec;
		this.initialValue = initialValue;
	}

	public Codec<T> codec() {
		return codec;
	}

	public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
		return streamCodec;
	}

	public T initialValue() {
		return initialValue;
	}

	public Identifier key() {
		return SupporterInit.SUPPORTER_DATA_TYPE.getKey(this);
	}
}
