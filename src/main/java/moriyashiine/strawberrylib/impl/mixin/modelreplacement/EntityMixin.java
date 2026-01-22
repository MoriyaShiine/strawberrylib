/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	public abstract Level level();

	@SuppressWarnings("CancellableInjectionUsage")
	@Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
	protected void slib$modelReplacementStepSound(BlockPos pos, BlockState blockState, CallbackInfo ci) {
	}

	@SuppressWarnings("CancellableInjectionUsage")
	@Inject(method = "playMuffledStepSound", at = @At("HEAD"), cancellable = true)
	protected void slib$modelReplacementMuffledStepSound(BlockState blockState, CallbackInfo ci) {
	}

	@SuppressWarnings("ConstantValue")
	@ModifyExpressionValue(method = "getEncodeId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;canSerialize()Z"))
	private boolean slib$modelReplacement(boolean original) {
		return original && level().players().stream().noneMatch(player -> (Object) this == SLibUtils.getModelReplacement(player));
	}
}
