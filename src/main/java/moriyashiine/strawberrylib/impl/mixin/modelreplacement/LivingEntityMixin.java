/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {
	@Inject(method = "playHurtSound", at = @At("TAIL"))
	protected void slib$modelReplacementHurtSound(DamageSource source, CallbackInfo ci) {
	}

	@ModifyReturnValue(method = "getDimensions", at = @At("RETURN"))
	protected EntityDimensions slib$modelReplacementDimensions(EntityDimensions original, Pose pose) {
		return original;
	}
}
