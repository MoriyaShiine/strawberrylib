/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.common.component.entity.ModelReplacementComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
	@Inject(method = "updateRenderState", at = @At("TAIL"))
	private void slib$modelReplacement(T entity, S state, float tickProgress, CallbackInfo ci) {
		if (ModelReplacementComponent.enableRenderCheck) {
			MinecraftClient client = MinecraftClient.getInstance();
			for (AbstractClientPlayerEntity player : client.world.getPlayers()) {
				if (SLibUtils.getModelReplacement(player) == entity) {
					state.x = MathHelper.lerp(tickProgress, player.lastRenderX, player.getX());
					state.y = MathHelper.lerp(tickProgress, player.lastRenderY, player.getY());
					state.z = MathHelper.lerp(tickProgress, player.lastRenderZ, player.getZ());
					state.squaredDistanceToCamera = player.squaredDistanceTo(client.gameRenderer.getCamera().getPos());
					return;
				}
			}
		}
	}
}
