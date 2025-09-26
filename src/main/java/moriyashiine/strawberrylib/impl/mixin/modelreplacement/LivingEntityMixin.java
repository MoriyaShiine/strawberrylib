/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {
	@Shadow
	public abstract boolean isPartOfGame();

	@Inject(method = "playHurtSound", at = @At("TAIL"))
	protected void slib$modelReplacementHurtSound(DamageSource damageSource, CallbackInfo ci) {
	}

	@ModifyReturnValue(method = "getDimensions", at = @At("RETURN"))
	protected EntityDimensions slib$modelReplacementDimensions(EntityDimensions original, EntityPose pose) {
		return original;
	}
}
