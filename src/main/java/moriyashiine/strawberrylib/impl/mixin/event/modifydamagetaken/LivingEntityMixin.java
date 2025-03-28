/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.modifydamagetaken;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
	private float slib$modifyDamageTaken(float amount, ServerWorld world, DamageSource source) {
		return ModifyDamageTakenEvent.Base.getModifiedDamage(amount, world, source, (LivingEntity) (Object) this);
	}

	@ModifyReturnValue(method = "modifyAppliedDamage", at = @At("RETURN"))
	private float slib$modifyDamageTaken(float original, DamageSource source) {
		if (getWorld() instanceof ServerWorld world) {
			return ModifyDamageTakenEvent.Final.getModifiedDamage(original, world, source, (LivingEntity) (Object) this);
		}
		return original;
	}
}
