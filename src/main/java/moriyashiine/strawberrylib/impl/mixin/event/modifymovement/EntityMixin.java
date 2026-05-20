/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.modifymovement;

import moriyashiine.strawberrylib.api.event.ModifyMovementEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public class EntityMixin {
	@SuppressWarnings("ConstantValue")
	@ModifyVariable(method = "move", at = @At("HEAD"), argsOnly = true)
	private Vec3 slib$modifyMovement(Vec3 delta, MoverType moverType) {
		if (moverType == MoverType.SELF && (Object) this instanceof LivingEntity living) {
			return ModifyMovementEvents.MOVEMENT_DELTA.invoker().modify(delta, living);
		}
		return delta;
	}
}
