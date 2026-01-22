/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.client.addnightvisionscale;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.client.AddNightVisionScaleEvent;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "hasEffect", at = @At("RETURN"))
	private boolean slib$addNightVisionScale(boolean original, Holder<MobEffect> effect) {
		if (!original && effect.equals(MobEffects.NIGHT_VISION) && AddNightVisionScaleEvent.EVENT.invoker().addScale((LivingEntity) (Object) this) > 0) {
			return true;
		}
		return original;
	}
}
