/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	public abstract World getEntityWorld();

	@SuppressWarnings("CancellableInjectionUsage")
	@Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
	protected void slib$modelReplacementStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
	}

	@SuppressWarnings("CancellableInjectionUsage")
	@Inject(method = "playSecondaryStepSound", at = @At("HEAD"), cancellable = true)
	protected void slib$modelReplacementStepSound(BlockState state, CallbackInfo ci) {
	}

	@SuppressWarnings("ConstantValue")
	@ModifyExpressionValue(method = "getSavedEntityId", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;isSaveable()Z"))
	private boolean slib$modelReplacement(boolean original) {
		return original && getEntityWorld().getPlayers().stream().noneMatch(player -> (Object) this == SLibUtils.getModelReplacement(player));
	}
}
