/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.module;

import com.mojang.serialization.Codec;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataType;
import moriyashiine.strawberrylib.api.supporter.objects.client.ClientSupporterData;
import moriyashiine.strawberrylib.impl.client.supporter.ClientSupporterInit;
import moriyashiine.strawberrylib.impl.common.init.StrawberryLibEntityComponents;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.minecraft.client.OptionInstance;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public final class SLibSupporterUtils {
	public static boolean isSupporter(Player player) {
		return SupporterInit.isSupporter(player);
	}

	public static <T> SupporterDataType<T> registerDataType(Identifier id, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, T initialValue) {
		return Registry.register(SupporterInit.SUPPORTER_DATA_TYPE, id, new SupporterDataType<>(codec, streamCodec, initialValue));
	}

	public static <T> T getData(Player player, SupporterDataType<T> type) {
		return StrawberryLibEntityComponents.SUPPORTER.get(player).getValue(type);
	}

	public static <T> void registerOption(SupporterDataType<T> type, OptionInstance<T> option) {
		ClientSupporterInit.OPTIONS.put(type, (new ClientSupporterData<>(type.key().toString().replace(":", "."), option)));
	}

	public static <T> void registerOption(SupporterDataType<T> type, OptionInstance.CaptionBasedToString<T> toString, OptionInstance.ValueSet<T> values, T initialValue) {
		OptionInstance<T> option = createOption(type, toString, values, initialValue);
		registerOption(type, option);
	}

	public static <T> OptionInstance<T> createOption(SupporterDataType<T> type, OptionInstance.CaptionBasedToString<T> toString, OptionInstance.ValueSet<T> values, T initialValue) {
		return new OptionInstance<>("options." + type.key().toString().replace(":", "."), OptionInstance.noTooltip(), toString, values, initialValue, _ -> {
		});
	}
}
