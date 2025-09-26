/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.modifycriticalstatus;

import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 2)
	private boolean slib$modifyCriticalStatus(boolean value, Entity target) {
		TriState state = ModifyCriticalStatusEvent.EVENT.invoker().isCritical((PlayerEntity) (Object) this, target, StrawberryLib.currentAttackCooldown);
		if (state != TriState.DEFAULT) {
			return state.get();
		}
		return value;
	}
}
