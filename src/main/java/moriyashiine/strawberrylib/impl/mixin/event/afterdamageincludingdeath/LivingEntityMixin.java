/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.afterdamageincludingdeath;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "hurtServer", at = @At("TAIL"))
	private void slib$afterDamageIncludingDeath(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir, @Local(name = "originalDamage") float originalDamage, @Local(name = "blocked") boolean blocked) {
		LivingEntity victim = (LivingEntity) (Object) this;
		float modifiedDamage = ModifyDamageTakenEvent.getModifiedDamage(ModifyDamageTakenEvent.Phase.FINAL, damage, victim, level, source);
		AfterDamageIncludingDeathEvent.EVENT.invoker().afterDamage(victim, source, originalDamage, modifiedDamage, blocked);
	}
}
