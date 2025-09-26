/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static moriyashiine.strawberrylib.impl.client.StrawberrylibClient.currentPlayerRenderState;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {
	@Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
	private void slib$modelReplacement(T entity, S state, float tickProgress, CallbackInfo ci) {
		if (currentPlayerRenderState != null) {
			// Entity
			state.x = currentPlayerRenderState.x;
			state.y = currentPlayerRenderState.y;
			state.z = currentPlayerRenderState.z;
			state.squaredDistanceToCamera = currentPlayerRenderState.squaredDistanceToCamera;
			// LivingEntity
			state.bodyYaw = currentPlayerRenderState.bodyYaw;
			state.relativeHeadYaw = currentPlayerRenderState.relativeHeadYaw;
			state.pitch = currentPlayerRenderState.pitch;
		}
	}
}
