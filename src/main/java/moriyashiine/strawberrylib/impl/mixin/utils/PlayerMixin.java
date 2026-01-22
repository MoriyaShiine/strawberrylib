/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.utils;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
	@WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAttackStrengthScale(F)F"))
	private float slib$currentAttackCooldown(Player instance, float a, Operation<Float> original) {
		float value = original.call(instance, a);
		StrawberryLib.currentAttackCooldown = value;
		return value;
	}

	@Inject(method = "attack", at = @At("TAIL"))
	private void slib$currentAttackCooldown(Entity entity, CallbackInfo ci) {
		StrawberryLib.currentAttackCooldown = -1;
	}
}
