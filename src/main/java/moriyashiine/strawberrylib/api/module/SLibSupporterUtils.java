/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.module;

import com.mojang.serialization.Codec;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterData;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataKey;
import moriyashiine.strawberrylib.api.supporter.objects.client.ClientSupporterData;
import moriyashiine.strawberrylib.impl.client.supporter.ClientSupporterInit;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.impl.common.supporter.component.entity.SupporterComponent;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public final class SLibSupporterUtils {
	public static boolean isSupporter(PlayerEntity player) {
		return SupporterInit.isSupporter(player);
	}

	public static <T> SupporterDataKey<T> registerData(Identifier id, Codec<T> codec, T defaultValue) {
		SupporterDataKey<T> key = new SupporterDataKey<>(id);
		SupporterInit.DATA.put(key, new SupporterData<>(key, codec, defaultValue));
		return key;
	}

	public static <T> T getData(PlayerEntity player, SupporterDataKey<T> key) {
		return ModEntityComponents.SUPPORTER.get(player).getData(key).getValue();
	}

	public static <T> void setData(PlayerEntity player, SupporterDataKey<T> key, T value) {
		SupporterComponent supporterComponent = ModEntityComponents.SUPPORTER.get(player);
		supporterComponent.getData(key).setValue(value);
		supporterComponent.sync();
	}

	public static <T> void registerOption(SupporterDataKey<T> key, SimpleOption<T> option, ClientSupporterData.Reader<T> reader, ClientSupporterData.Writer<T> writer, ClientSupporterData.PayloadSender<T> payloadSender) {
		ClientSupporterInit.OPTIONS.put(key, (new ClientSupporterData<>(key.id().toString().replace(":", "."), option, option.getValue(), reader, writer, payloadSender)));
	}

	public static <T> void registerOption(SupporterDataKey<T> key, SimpleOption.ValueTextGetter<T> valueTextGetter, SimpleOption.Callbacks<T> callbacks, T defaultValue, ClientSupporterData.Reader<T> reader, ClientSupporterData.Writer<T> writer, ClientSupporterData.PayloadSender<T> payloadSender) {
		SimpleOption<T> option = createOption(key, valueTextGetter, callbacks, defaultValue);
		registerOption(key, option, reader, writer, payloadSender);
	}

	public static <T> SimpleOption<T> createOption(SupporterDataKey<T> key, SimpleOption.ValueTextGetter<T> valueTextGetter, SimpleOption.Callbacks<T> callbacks, T defaultValue) {
		return new SimpleOption<>("options." + key.id().toString().replace(":", "."), SimpleOption.emptyTooltip(), valueTextGetter, callbacks, defaultValue, v -> {
		});
	}
}
