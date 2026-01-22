/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import moriyashiine.strawberrylib.impl.common.component.entity.ModelReplacementComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntity.class, Mob.class})
public abstract class DisableTickMixin {
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void slib$modelReplacement(CallbackInfo ci) {
		if (ModelReplacementComponent.disableTick) {
			ci.cancel();
		}
	}
}
