/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import moriyashiine.strawberrylib.impl.client.supporter.render.entity.state.ModelReplacementAddition;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderManager.class)
public abstract class EntityRenderManagerMixin {
	@Shadow
	public abstract <S extends EntityRenderState> void render(S renderState, CameraRenderState cameraRenderState, double x, double y, double z, MatrixStack matrices, OrderedRenderCommandQueue queue);

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private <S extends EntityRenderState> void slib$modelReplacement(S renderState, CameraRenderState cameraRenderState, double x, double y, double z, MatrixStack matrices, OrderedRenderCommandQueue queue, CallbackInfo ci) {
		if (renderState instanceof PlayerEntityRenderState playerRenderState && !playerRenderState.spectator) {
			LivingEntityRenderState replacementState = ((ModelReplacementAddition) playerRenderState).slib$getReplacementRenderState();
			if (replacementState != null) {
				render(replacementState, cameraRenderState, x, y, z, matrices, queue);
				ci.cancel();
			}
		}
	}
}
