/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import moriyashiine.strawberrylib.impl.common.component.entity.ModelReplacementComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class, Mob.class})
public class DisableAttackMixin {
	@Inject(method = "doHurtTarget", at = @At("HEAD"), cancellable = true)
	private void slib$modelReplacement(ServerLevel level, Entity target, CallbackInfoReturnable<Boolean> cir) {
		if (ModelReplacementComponent.disableAttack) {
			cir.setReturnValue(false);
		}
	}
}
