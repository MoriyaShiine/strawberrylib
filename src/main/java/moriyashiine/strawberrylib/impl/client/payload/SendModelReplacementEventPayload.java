/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.client.payload;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record SendModelReplacementEventPayload(int entityId, byte event) implements CustomPacketPayload {
	public static final Type<SendModelReplacementEventPayload> TYPE = new Type<>(StrawberryLib.id("send_model_replacement_event"));
	public static final StreamCodec<FriendlyByteBuf, SendModelReplacementEventPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SendModelReplacementEventPayload::entityId,
			ByteBufCodecs.BYTE, SendModelReplacementEventPayload::event,
			SendModelReplacementEventPayload::new);

	@Override
	public Type<SendModelReplacementEventPayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer receiver, ServerPlayer player, byte event) {
		ServerPlayNetworking.send(receiver, new SendModelReplacementEventPayload(player.getId(), event));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<SendModelReplacementEventPayload> {
		@Override
		public void receive(SendModelReplacementEventPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().level().getEntity(payload.entityId());
			if (entity instanceof Player player) {
				SLibUtils.getModelReplacement(player).handleEntityEvent(payload.event());
			}
		}
	}
}
