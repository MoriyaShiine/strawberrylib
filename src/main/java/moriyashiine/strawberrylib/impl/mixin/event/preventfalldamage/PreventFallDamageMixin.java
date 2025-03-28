/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.preventfalldamage;

import moriyashiine.strawberrylib.api.event.PreventFallDamageEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class, AbstractHorseEntity.class})
public abstract class PreventFallDamageMixin extends Entity {
	public PreventFallDamageMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
	private void slib$preventFallDamage(double fallDistance, float damagePerDistance, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
		if (PreventFallDamageEvent.EVENT.invoker().shouldNotTakeFallDamage(getWorld(), (LivingEntity) (Object) this, fallDistance, damagePerDistance, damageSource)) {
			cir.setReturnValue(false);
		}
	}
}
