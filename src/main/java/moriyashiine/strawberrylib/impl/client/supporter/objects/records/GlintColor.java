/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.client.supporter.objects.records;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum GlintColor implements StringRepresentable {
	BLUE("blue"),
	BROWN("brown"),
	CYAN("cyan"),
	GRAY("gray"),
	GREEN("green"),
	LIGHT_BLUE("light_blue"),
	LIME("lime"),
	MAGENTA("magenta"),
	ORANGE("orange"),
	PINK("pink"),
	PURPLE("purple"),
	RED("red"),
	YELLOW("yellow");

	private static final IntFunction<GlintColor> BY_ID = ByIdMap.continuous(GlintColor::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final Codec<GlintColor> CODEC = StringRepresentable.fromEnum(GlintColor::values);
	public static final StreamCodec<ByteBuf, GlintColor> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, GlintColor::ordinal);

	private final String name;

	GlintColor(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Component getOptionsName() {
		return Component.translatable("color.minecraft." + getName());
	}

	@Override
	public String getSerializedName() {
		return getName();
	}
}
