/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.common.supporter.payload;

import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SyncGlintColorPayload(boolean equippable, GlintColor color) implements CustomPacketPayload {
	public static final Type<SyncGlintColorPayload> TYPE = new Type<>(StrawberryLib.id("sync_glint_color"));
	public static final StreamCodec<FriendlyByteBuf, SyncGlintColorPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL, SyncGlintColorPayload::equippable,
			GlintColor.STREAM_CODEC, SyncGlintColorPayload::color,
			SyncGlintColorPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void send(boolean equippable, GlintColor color) {
		ClientPlayNetworking.send(new SyncGlintColorPayload(equippable, color));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SyncGlintColorPayload> {
		@Override
		public void receive(SyncGlintColorPayload payload, ServerPlayNetworking.Context context) {
			if (SLibSupporterUtils.isSupporter(context.player())) {
				SLibSupporterUtils.setData(context.player(), payload.equippable() ? SupporterInit.EQUIPPABLE_GLINT_COLOR : SupporterInit.GLINT_COLOR, payload.color());
			}
		}
	}
}
