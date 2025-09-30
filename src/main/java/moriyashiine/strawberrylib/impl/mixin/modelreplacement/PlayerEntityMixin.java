/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {
	@Override
	protected void slib$modelReplacementStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
		if (SLibUtils.getModelReplacement((PlayerEntity) (Object) this) instanceof LivingEntity replacement) {
			replacement.playStepSound(pos, state);
			ci.cancel();
		}
	}

	@Override
	protected void slib$modelReplacementStepSound(BlockState state, CallbackInfo ci) {
		if (SLibUtils.getModelReplacement((PlayerEntity) (Object) this) instanceof LivingEntity replacement) {
			replacement.playSecondaryStepSound(state);
			ci.cancel();
		}
	}

	@Override
	protected void slib$modelReplacementHurtSound(DamageSource damageSource, CallbackInfo ci) {
		if (SLibUtils.getModelReplacement((PlayerEntity) (Object) this) instanceof MobEntity mob) {
			ModEntityComponents.MODEL_REPLACEMENT.get(this).resetSoundDelay(mob);
		}
	}

	@Override
	protected EntityDimensions slib$modelReplacementDimensions(EntityDimensions original, EntityPose pose) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		if (player.isPartOfGame()) {
			LivingEntity replacement = SLibUtils.getModelReplacement(player);
			if (replacement != null) {
				return replacement.getDimensions(pose);
			}
		}
		return super.slib$modelReplacementDimensions(original, pose);
	}

	@ModifyReturnValue(method = "getFallSounds", at = @At("RETURN"))
	private LivingEntity.FallSounds slib$modelReplacementFallSounds(LivingEntity.FallSounds original) {
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
