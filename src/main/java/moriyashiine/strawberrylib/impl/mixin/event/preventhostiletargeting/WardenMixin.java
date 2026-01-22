/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.preventhostiletargeting;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Warden.class)
public abstract class WardenMixin extends LivingEntity {
	protected WardenMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@ModifyReturnValue(method = "canTargetEntity", at = @At("RETURN"))
	private boolean slib$preventHostileTargeting(boolean original, Entity entity) {
		if (entity instanceof LivingEntity target && getLastHurtByMob() != target && PreventHostileTargetingEvent.EVENT.invoker().preventsTargeting(this, target).get()) {
			return false;
		}
		return original;
	}
}
