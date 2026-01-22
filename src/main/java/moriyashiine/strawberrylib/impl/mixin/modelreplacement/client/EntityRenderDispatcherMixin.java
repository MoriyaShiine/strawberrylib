/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.entity.state.ModelReplacementRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
	@Shadow
	public abstract <S extends EntityRenderState> void submit(S renderState, CameraRenderState camera, double x, double y, double z, PoseStack poseStack, SubmitNodeCollector submitNodeCollector);

	@Inject(method = "submit", at = @At("HEAD"), cancellable = true)
	private <S extends EntityRenderState> void slib$modelReplacement(S renderState, CameraRenderState camera, double x, double y, double z, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CallbackInfo ci) {
		if (renderState instanceof AvatarRenderState playerRenderState && !playerRenderState.isSpectator) {
			@Nullable ModelReplacementRenderState modelReplacementRenderState = playerRenderState.getData(ModelReplacementRenderState.KEY);
			if (modelReplacementRenderState != null) {
				LivingEntityRenderState replacementState = modelReplacementRenderState.replacementRenderState;
				if (replacementState != null) {
					submit(replacementState, camera, x, y, z, poseStack, submitNodeCollector);
					ci.cancel();
				}
			}
		}
	}
}
