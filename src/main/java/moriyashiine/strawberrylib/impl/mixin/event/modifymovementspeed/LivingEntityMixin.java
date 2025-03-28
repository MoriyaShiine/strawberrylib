/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.modifymovementspeed;

import moriyashiine.strawberrylib.api.event.ModifyMovementSpeedEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@ModifyVariable(method = "travel", at = @At("HEAD"), argsOnly = true)
	private Vec3d slib$modifyMovementSpeed(Vec3d value) {
		return ModifyMovementSpeedEvent.EVENT.invoker().modify(value, (LivingEntity) (Object) this);
	}
}
