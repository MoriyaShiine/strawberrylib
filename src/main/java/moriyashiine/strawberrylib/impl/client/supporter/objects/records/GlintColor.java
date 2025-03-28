/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.supporter.objects.records;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum GlintColor implements StringIdentifiable {
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

	private static final IntFunction<GlintColor> INDEX_MAPPER = ValueLists.createIndexToValueFunction(GlintColor::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);
	public static final Codec<GlintColor> CODEC = StringIdentifiable.createCodec(GlintColor::values);
	public static final PacketCodec<ByteBuf, GlintColor> PACKET_CODEC = PacketCodecs.indexed(INDEX_MAPPER, GlintColor::ordinal);

	private final String name;

	GlintColor(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Text getOptionsName() {
		return Text.translatable("color.minecraft." + getName());
	}

	@Override
	public String asString() {
		return getName();
	}
}
