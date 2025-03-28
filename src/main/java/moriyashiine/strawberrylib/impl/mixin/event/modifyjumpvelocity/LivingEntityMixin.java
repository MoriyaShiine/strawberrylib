/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.modifyjumpvelocity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.strawberrylib.api.event.ModifyJumpVelocityEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@WrapOperation(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V"))
	private void slib$modifyJumpVelocity(LivingEntity instance, double x, double y, double z, Operation<Void> original) {
		Vec3d modified = ModifyJumpVelocityEvent.EVENT.invoker().modify(new Vec3d(x, y, z), instance);
		original.call(instance, modified.getX(), modified.getY(), modified.getZ());
	}
}
