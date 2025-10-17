/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.afterdamageincludingdeath;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "damage", at = @At("TAIL"))
	private void slib$afterDamageIncludingDeath(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 1) float dealt, @Local(ordinal = 0) boolean blocked) {
		LivingEntity living = (LivingEntity) (Object) this;
		float modifiedDamage = ModifyDamageTakenEvent.getModifiedDamage(ModifyDamageTakenEvent.Phase.FINAL, amount, world, source, living);
		AfterDamageIncludingDeathEvent.EVENT.invoker().afterDamage(living, source, dealt, modifiedDamage, blocked);
	}
}
