/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.modifydestroyspeed;

import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {
	@Inject(method = "getDestroyProgress", at = @At("HEAD"))
	private void slib$modifyDestroySpeed(BlockState state, Player player, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Float> cir) {
		StrawberryLib.currentEventDestroyPos = pos;
	}

	@Inject(method = "getDestroyProgress", at = @At(value = "RETURN", ordinal = 1))
	private void slib$modifyDestroySpeed(CallbackInfoReturnable<Float> cir) {
		StrawberryLib.currentEventDestroyPos = null;
	}
}
