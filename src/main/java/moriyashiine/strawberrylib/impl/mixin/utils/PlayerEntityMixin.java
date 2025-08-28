/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.utils;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"))
	private float slib$currentAttackCooldown(PlayerEntity instance, float baseTime, Operation<Float> original) {
		float value = original.call(instance, baseTime);
		SLibUtils.currentAttackCooldown = value;
		return value;
	}

	@Inject(method = "attack", at = @At("TAIL"))
	private void slib$currentAttackCooldown(Entity target, CallbackInfo ci) {
		SLibUtils.currentAttackCooldown = -1;
	}
}
