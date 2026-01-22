/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.modifycriticalstatus;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {
	@ModifyReturnValue(method = "canCriticalAttack", at = @At("RETURN"))
	private boolean slib$modifyCriticalStatus(boolean original, Entity entity) {
		TriState state = ModifyCriticalStatusEvent.EVENT.invoker().isCritical((Player) (Object) this, entity, StrawberryLib.currentAttackCooldown);
		if (state != TriState.DEFAULT) {
			return state.get();
		}
		return original;
	}
}
