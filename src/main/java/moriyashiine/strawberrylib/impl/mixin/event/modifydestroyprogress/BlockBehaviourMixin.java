/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.modifydestroyprogress;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.ModifyDestroyProgressEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {
	@ModifyReturnValue(method = "getDestroyProgress", at = @At("RETURN"))
	private float slib$modifyDestroyProgress(float original, BlockState state, Player player, BlockGetter level, BlockPos pos) {
		return ModifyDestroyProgressEvent.getModifiedDestroyProgress(original, player, state, level, pos);
	}
}
