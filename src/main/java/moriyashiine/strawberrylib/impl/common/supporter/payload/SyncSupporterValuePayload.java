/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.common.supporter.payload;

import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataType;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.init.StrawberryLibEntityComponents;
import moriyashiine.strawberrylib.impl.common.supporter.component.entity.SupporterComponent;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record SyncSupporterValuePayload<T>(SupporterDataType<T> supporterDataType, T value) implements CustomPacketPayload {
	public static final Type<SyncSupporterValuePayload<?>> TYPE = new Type<>(StrawberryLib.id("sync_supporter_value"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SyncSupporterValuePayload<?>> CODEC = new StreamCodec<>() {
		@Override
		public SyncSupporterValuePayload<?> decode(RegistryFriendlyByteBuf input) {
			SupporterDataType<?> type = SupporterDataType.STREAM_CODEC.decode(input);
			return decode(input, type);
		}

		@Override
		public void encode(RegistryFriendlyByteBuf output, SyncSupporterValuePayload<?> value) {
			encode(value, output);
		}

		private static <T> SyncSupporterValuePayload<T> decode(RegistryFriendlyByteBuf input, SupporterDataType<T> type) {
			return new SyncSupporterValuePayload<>(type, type.streamCodec().decode(input));
		}

		private static <T> void encode(SyncSupporterValuePayload<T> payload, RegistryFriendlyByteBuf output) {
			SupporterDataType.STREAM_CODEC.encode(output, payload.supporterDataType());
			payload.supporterDataType().streamCodec().encode(output, payload.value());
		}
	};

	@Override
	public Type<SyncSupporterValuePayload<?>> type() {
		return TYPE;
	}

	public static <T> void send(SupporterDataType<T> type, T value) {
		ClientPlayNetworking.send(new SyncSupporterValuePayload<>(type, value));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SyncSupporterValuePayload<?>> {
		@Override
		public void receive(SyncSupporterValuePayload<?> payload, ServerPlayNetworking.Context context) {
			if (SLibSupporterUtils.isSupporter(context.player())) {
				receive(context.player(), payload);
			}
		}

		private static <T> void receive(Player player, SyncSupporterValuePayload<T> payload) {
			SupporterComponent supporter = StrawberryLibEntityComponents.SUPPORTER.get(player);
			supporter.setValue(payload.supporterDataType(), payload.value());
			supporter.sync();
		}
	}
}
