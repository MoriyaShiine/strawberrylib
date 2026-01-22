/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.client.payload.SendModelReplacementEventPayload;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
	@Shadow
	public abstract List<ServerPlayer> players();

	@Inject(method = "broadcastEntityEvent", at = @At("HEAD"), cancellable = true)
	private void slib$modelReplacement(Entity entity, byte event, CallbackInfo ci) {
		for (ServerPlayer player : players()) {
			if (SLibUtils.getModelReplacement(player) == entity) {
				PlayerLookup.tracking(player).forEach(receiver -> SendModelReplacementEventPayload.send(receiver, player, event));
				SendModelReplacementEventPayload.send(player, player, event);
				ci.cancel();
				return;
			}
		}
	}
}
