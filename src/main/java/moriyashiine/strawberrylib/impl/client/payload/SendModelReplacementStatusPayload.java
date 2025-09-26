/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.payload;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public record SendModelReplacementStatusPayload(int entityId, byte entityStatus) implements CustomPayload {
	public static final Id<SendModelReplacementStatusPayload> ID = new Id<>(StrawberryLib.id("send_model_replacement_status"));
	public static final PacketCodec<PacketByteBuf, SendModelReplacementStatusPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, SendModelReplacementStatusPayload::entityId,
			PacketCodecs.BYTE, SendModelReplacementStatusPayload::entityStatus,
			SendModelReplacementStatusPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(ServerPlayerEntity receiver, ServerPlayerEntity player, byte entityStatus) {
		ServerPlayNetworking.send(receiver, new SendModelReplacementStatusPayload(player.getId(), entityStatus));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<SendModelReplacementStatusPayload> {
		@Override
		public void receive(SendModelReplacementStatusPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().getEntityWorld().getEntityById(payload.entityId());
			if (entity instanceof PlayerEntity player) {
				SLibUtils.getModelReplacement(player).handleStatus(payload.entityStatus());
			}
		}
	}
}
