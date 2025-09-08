/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@SuppressWarnings("ConstantValue")
	@Inject(method = "playHurtSound", at = @At("TAIL"))
	private void slib$modelReplacement(DamageSource damageSource, CallbackInfo ci) {
		if ((Object) this instanceof PlayerEntity player && SLibUtils.getModelReplacement(player) instanceof MobEntity mob) {
			ModEntityComponents.MODEL_REPLACEMENT.get(player).resetSoundDelay(mob);
		}
	}
}
