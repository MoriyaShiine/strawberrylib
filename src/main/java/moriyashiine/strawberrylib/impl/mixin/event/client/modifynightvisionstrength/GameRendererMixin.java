/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.client.modifynightvisionstrength;

import moriyashiine.strawberrylib.api.event.client.ModifyNightVisionStrengthEvent;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@Unique
	private static boolean checkingStrength = false;

	@Shadow
	public static float getNightVisionStrength(LivingEntity entity, float tickProgress) {
		return 0;
	}

	@Inject(method = "getNightVisionStrength", at = @At("HEAD"), cancellable = true)
	private static void slib$modifyNightVisionStrength(LivingEntity entity, float tickProgress, CallbackInfoReturnable<Float> cir) {
		if (checkingStrength) {
			return;
		}
		float strength = ModifyNightVisionStrengthEvent.getModifiedNightVisionStrength(0, entity);
		if (strength > 0) {
			float original = 0;
			if (entity.getActiveStatusEffects().containsKey(StatusEffects.NIGHT_VISION)) {
				checkingStrength = true;
				original = getNightVisionStrength(entity, tickProgress);
				checkingStrength = false;
			}
			cir.setReturnValue(MathHelper.clamp(strength, original, 1));
		}
	}
}