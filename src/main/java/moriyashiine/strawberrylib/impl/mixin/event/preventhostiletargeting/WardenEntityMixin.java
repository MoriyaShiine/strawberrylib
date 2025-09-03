/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.preventhostiletargeting;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin extends LivingEntity {
	protected WardenEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyReturnValue(method = "isValidTarget", at = @At("RETURN"))
	private boolean slib$preventHostileTargeting(boolean original, Entity entity) {
		if (entity instanceof LivingEntity target && getAttacker() != target && PreventHostileTargetingEvent.EVENT.invoker().preventsTargeting(this, target).get()) {
			return false;
		}
		return original;
	}
}
