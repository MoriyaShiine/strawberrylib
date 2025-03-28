/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.client.modifynightvisionstrength;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.client.ModifyNightVisionStrengthEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "hasStatusEffect", at = @At("RETURN"))
	private boolean slib$modifyNightVisionStrength(boolean original, RegistryEntry<StatusEffect> effect) {
		if (effect.equals(StatusEffects.NIGHT_VISION) && ModifyNightVisionStrengthEvent.getModifiedNightVisionStrength(0, (LivingEntity) (Object) this) != 0) {
			return true;
		}
		return original;
	}
}
