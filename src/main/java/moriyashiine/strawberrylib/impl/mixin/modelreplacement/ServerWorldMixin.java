/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.client.payload.SendModelReplacementStatusPayload;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	@Shadow
	@Final
	List<ServerPlayerEntity> players;

	@Inject(method = "sendEntityStatus", at = @At("HEAD"), cancellable = true)
	private void slib$modelReplacement(Entity entity, byte status, CallbackInfo ci) {
		for (ServerPlayerEntity player : players) {
			if (SLibUtils.getModelReplacement(player) == entity) {
				PlayerLookup.tracking(player).forEach(receiver -> SendModelReplacementStatusPayload.send(receiver, player, status));
				SendModelReplacementStatusPayload.send(player, player, status);
				ci.cancel();
				return;
			}
		}
	}
}
