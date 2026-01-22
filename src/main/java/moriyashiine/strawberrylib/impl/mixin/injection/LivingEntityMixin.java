/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.injection;

import moriyashiine.strawberrylib.impl.common.injection.SLibLivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements SLibLivingEntity {
	@Shadow
	public abstract boolean canBeSeenAsEnemy();

	@Shadow
	public abstract boolean canBeSeenByAnyone();

	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Override
	public boolean slib$isPlayer() {
		return isAlwaysTicking();
	}

	@Override
	public boolean slib$isSurvival() {
		return canBeSeenAsEnemy();
	}

	@Override
	public boolean slib$exists() {
		return canBeSeenByAnyone();
	}
}
