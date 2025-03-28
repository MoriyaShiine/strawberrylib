/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common.supporter.payload;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.impl.common.supporter.component.entity.SupporterComponent;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record SyncGlintColorPayload(boolean equippable, GlintColor color) implements CustomPayload {
	public static final Id<SyncGlintColorPayload> ID = new Id<>(StrawberryLib.id("sync_glint_color"));
	public static final PacketCodec<PacketByteBuf, SyncGlintColorPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.BOOLEAN, SyncGlintColorPayload::equippable,
			GlintColor.PACKET_CODEC, SyncGlintColorPayload::color,
			SyncGlintColorPayload::new
	);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(boolean equippable, GlintColor color) {
		ClientPlayNetworking.send(new SyncGlintColorPayload(equippable, color));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SyncGlintColorPayload> {
		@Override
		public void receive(SyncGlintColorPayload payload, ServerPlayNetworking.Context context) {
			if (SupporterInit.isSupporter(context.player())) {
				SupporterComponent supporterComponent = ModEntityComponents.SUPPORTER.get(context.player());
				if (payload.equippable()) {
					supporterComponent.setEquippableGlintColor(payload.color());
				} else {
					supporterComponent.setGlintColor(payload.color());
				}
				supporterComponent.sync();
			}
		}
	}
}
