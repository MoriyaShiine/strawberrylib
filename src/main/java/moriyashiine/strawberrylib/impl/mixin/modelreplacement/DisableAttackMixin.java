/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import moriyashiine.strawberrylib.impl.common.component.entity.ModelReplacementComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class, MobEntity.class})
public class DisableAttackMixin {
	@Inject(method = "tryAttack", at = @At("HEAD"), cancellable = true)
	private void slib$modelReplacement(ServerWorld world, Entity target, CallbackInfoReturnable<Boolean> cir) {
		if (ModelReplacementComponent.disableAttack) {
			cir.setReturnValue(false);
		}
	}
}
