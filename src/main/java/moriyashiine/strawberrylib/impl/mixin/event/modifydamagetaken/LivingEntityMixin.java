/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.modifydamagetaken;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true)
	private float slib$modifyDamageTaken(float damage, ServerLevel level, DamageSource source) {
		return ModifyDamageTakenEvent.getModifiedDamage(ModifyDamageTakenEvent.Phase.BASE, damage, (LivingEntity) (Object) this, level, source);
	}

	@ModifyReturnValue(method = "getDamageAfterMagicAbsorb", at = @At("RETURN"))
	private float slib$modifyDamageTaken(float original, DamageSource damageSource) {
		if (level() instanceof ServerLevel level) {
			return ModifyDamageTakenEvent.getModifiedDamage(ModifyDamageTakenEvent.Phase.FINAL, original, (LivingEntity) (Object) this, level, damageSource);
		}
		return original;
	}
}
