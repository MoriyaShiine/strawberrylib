/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyReturnValue(method = "getBaseDimensions", at = @At("RETURN"))
	private EntityDimensions slib$modelReplacement(EntityDimensions original, EntityPose pose) {
		if (isPartOfGame()) {
			LivingEntity replacement = SLibUtils.getModelReplacement((PlayerEntity) (Object) this);
			if (replacement != null) {
				return replacement.getBaseDimensions(pose);
			}
		}
		return original;
	}

	@ModifyReturnValue(method = "getFallSounds", at = @At("RETURN"))
	private FallSounds slib$modelReplacementFallSounds(FallSounds original) {
		if (SLibUtils.getModelReplacement((PlayerEntity) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getFallSounds();
		}
		return original;
	}

	@ModifyReturnValue(method = "getHurtSound", at = @At("RETURN"))
	private SoundEvent slib$modelReplacementHurtSound(SoundEvent original, DamageSource source) {
		if (SLibUtils.getModelReplacement((PlayerEntity) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getHurtSound(source);
		}
		return original;
	}

	@ModifyReturnValue(method = "getDeathSound", at = @At("RETURN"))
	private SoundEvent slib$modelReplacementDeathSound(SoundEvent original) {
		if (SLibUtils.getModelReplacement((PlayerEntity) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getDeathSound();
		}
		return original;
	}

	@ModifyReturnValue(method = "getSwimSound", at = @At("RETURN"))
	private SoundEvent slib$modelReplacementSwimSound(SoundEvent original) {
		if (SLibUtils.getModelReplacement((PlayerEntity) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getSwimSound();
		}
		return original;
	}

	@ModifyReturnValue(method = "getSplashSound", at = @At("RETURN"))
	private SoundEvent slib$modelReplacementSplashSound(SoundEvent original) {
		if (SLibUtils.getModelReplacement((PlayerEntity) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getSplashSound();
		}
		return original;
	}

	@ModifyReturnValue(method = "getHighSpeedSplashSound", at = @At("RETURN"))
	private SoundEvent slib$modelReplacementHighSpeedSplashSound(SoundEvent original) {
		if (SLibUtils.getModelReplacement((PlayerEntity) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getHighSpeedSplashSound();
		}
		return original;
	}
}
