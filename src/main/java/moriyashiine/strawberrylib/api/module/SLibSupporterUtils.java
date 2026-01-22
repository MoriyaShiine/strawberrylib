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
import net.minecraft.client.OptionInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public final class SLibSupporterUtils {
	public static boolean isSupporter(Player player) {
		return SupporterInit.isSupporter(player);
	}

	public static <T> SupporterDataKey<T> registerData(Identifier id, Codec<T> codec, T defaultValue) {
		SupporterDataKey<T> key = new SupporterDataKey<>(id);
		SupporterInit.DATA.put(key, new SupporterData<>(key, codec, defaultValue));
		return key;
	}

	public static <T> T getData(Player player, SupporterDataKey<T> key) {
		return ModEntityComponents.SUPPORTER.get(player).getData(key).getValue();
	}

	public static <T> void setData(Player player, SupporterDataKey<T> key, T value) {
		SupporterComponent supporterComponent = ModEntityComponents.SUPPORTER.get(player);
		supporterComponent.getData(key).setValue(value);
		supporterComponent.sync();
	}

	public static <T> void registerOption(SupporterDataKey<T> key, OptionInstance<T> option, ClientSupporterData.Reader<T> reader, ClientSupporterData.Writer<T> writer, ClientSupporterData.PayloadSender<T> payloadSender) {
		ClientSupporterInit.OPTIONS.put(key, (new ClientSupporterData<>(key.id().toString().replace(":", "."), option, option.get(), reader, writer, payloadSender)));
	}

	public static <T> void registerOption(SupporterDataKey<T> key, OptionInstance.CaptionBasedToString<T> toString, OptionInstance.ValueSet<T> values, T initialValue, ClientSupporterData.Reader<T> reader, ClientSupporterData.Writer<T> writer, ClientSupporterData.PayloadSender<T> payloadSender) {
		OptionInstance<T> option = createOption(key, toString, values, initialValue);
		registerOption(key, option, reader, writer, payloadSender);
	}

	public static <T> OptionInstance<T> createOption(SupporterDataKey<T> key, OptionInstance.CaptionBasedToString<T> toString, OptionInstance.ValueSet<T> values, T initialValue) {
		return new OptionInstance<>("options." + key.id().toString().replace(":", "."), OptionInstance.noTooltip(), toString, values, initialValue, v -> {
		});
	}
}
