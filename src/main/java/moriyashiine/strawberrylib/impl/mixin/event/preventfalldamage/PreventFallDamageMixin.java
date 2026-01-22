/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.preventfalldamage;

import moriyashiine.strawberrylib.api.event.PreventFallDamageEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class, AbstractHorse.class})
public abstract class PreventFallDamageMixin extends Entity {
	public PreventFallDamageMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
	private void slib$preventFallDamage(double fallDistance, float damageModifier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
		if (PreventFallDamageEvent.EVENT.invoker().preventsFallDamage(level(), (LivingEntity) (Object) this, fallDistance, damageModifier, damageSource).get()) {
			cir.setReturnValue(false);
		}
	}
}
