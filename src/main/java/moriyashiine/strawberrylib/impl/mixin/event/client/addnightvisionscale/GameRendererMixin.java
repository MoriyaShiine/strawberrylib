/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.client.addnightvisionscale;

import moriyashiine.strawberrylib.api.event.client.AddNightVisionScaleEvent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@Unique
	private static boolean checkingScale = false;

	@Shadow
	public static float getNightVisionScale(LivingEntity camera, float a) {
		throw new UnsupportedOperationException();
	}

	@Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
	private static void slib$addNightVisionScale(LivingEntity camera, float a, CallbackInfoReturnable<Float> cir) {
		if (checkingScale) {
			return;
		}
		float scale = AddNightVisionScaleEvent.EVENT.invoker().addScale(camera);
		if (scale > 0) {
			float original = 0;
			if (camera.getActiveEffectsMap().containsKey(MobEffects.NIGHT_VISION)) {
				checkingScale = true;
				original = getNightVisionScale(camera, a);
				checkingScale = false;
			}
			cir.setReturnValue(Math.min(original + scale, 1));
		}
	}
}