/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.common.component.entity.ModelReplacementComponent;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntityMixin {
	@SuppressWarnings("ConstantValue")
	@Inject(method = "attack", at = @At("HEAD"))
	private void slib$modelReplacement(Entity entity, CallbackInfo ci) {
		if (level() instanceof ServerLevel level && SLibUtils.getModelReplacement((Player) (Object) this) instanceof LivingEntity replacement) {
			ModelReplacementComponent.disableAttack = true;
			replacement.doHurtTarget(level, entity);
			ModelReplacementComponent.disableAttack = false;
		}
	}

	@Override
	protected void slib$modelReplacementStepSound(BlockPos pos, BlockState blockState, CallbackInfo ci) {
		if (SLibUtils.getModelReplacement((Player) (Object) this) instanceof LivingEntity replacement) {
			replacement.playStepSound(pos, blockState);
			ci.cancel();
		}
	}

	@Override
	protected void slib$modelReplacementMuffledStepSound(BlockState blockState, CallbackInfo ci) {
		if (SLibUtils.getModelReplacement((Player) (Object) this) instanceof LivingEntity replacement) {
			replacement.playMuffledStepSound(blockState);
			ci.cancel();
		}
	}

	@Override
	protected void slib$modelReplacementHurtSound(DamageSource damageSource, CallbackInfo ci) {
		if (SLibUtils.getModelReplacement((Player) (Object) this) instanceof Mob mob) {
			ModEntityComponents.MODEL_REPLACEMENT.get(this).resetAmbientSoundTime(mob);
		}
	}

	@Override
	protected EntityDimensions slib$modelReplacementDimensions(EntityDimensions original, Pose pose) {
		Player player = (Player) (Object) this;
		if (player.slib$exists() && SLibUtils.getModelReplacement(player) instanceof LivingEntity replacement) {
			return replacement.getDimensions(pose);
		}
		return super.slib$modelReplacementDimensions(original, pose);
	}

	@ModifyReturnValue(method = "getFallSounds", at = @At("RETURN"))
	private LivingEntity.Fallsounds slib$modelReplacementFallSounds(LivingEntity.Fallsounds original) {
		if (SLibUtils.getModelReplacement((Player) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getFallSounds();
		}
		return original;
	}

	@ModifyReturnValue(method = "getHurtSound", at = @At("RETURN"))
	private SoundEvent slib$modelReplacementHurtSound(SoundEvent original, DamageSource source) {
		if (SLibUtils.getModelReplacement((Player) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getHurtSound(source);
		}
		return original;
	}

	@ModifyReturnValue(method = "getDeathSound", at = @At("RETURN"))
	private SoundEvent slib$modelReplacementDeathSound(SoundEvent original) {
		if (SLibUtils.getModelReplacement((Player) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getDeathSound();
		}
		return original;
	}

	@ModifyReturnValue(method = "getSwimSound", at = @At("RETURN"))
	private SoundEvent slib$modelReplacementSwimSound(SoundEvent original) {
		if (SLibUtils.getModelReplacement((Player) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getSwimSound();
		}
		return original;
	}

	@ModifyReturnValue(method = "getSwimSplashSound", at = @At("RETURN"))
	private SoundEvent slib$modelReplacementSwimSplashSound(SoundEvent original) {
		if (SLibUtils.getModelReplacement((Player) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getSwimSplashSound();
		}
		return original;
	}

	@ModifyReturnValue(method = "getSwimHighSpeedSplashSound", at = @At("RETURN"))
	private SoundEvent slib$modelReplacementSwimHighSpeedSplashSound(SoundEvent original) {
		if (SLibUtils.getModelReplacement((Player) (Object) this) instanceof LivingEntity replacement) {
			return replacement.getSwimHighSpeedSplashSound();
		}
		return original;
	}
}
