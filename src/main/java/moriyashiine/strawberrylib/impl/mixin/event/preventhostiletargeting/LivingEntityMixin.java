/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.preventhostiletargeting;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Unique
	private final Set<LivingEntity> attackers = new HashSet<>();

	@Inject(method = "tick", at = @At("TAIL"))
	private void slib$preventHostileTargeting(CallbackInfo ci) {
		attackers.removeIf(attacker -> attacker == null || !attacker.canTakeDamage());
	}

	@WrapOperation(method = "becomeAngry", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setAttacker(Lnet/minecraft/entity/LivingEntity;)V"))
	private void slib$preventHostileTargeting(LivingEntity instance, LivingEntity attacker, Operation<Void> original) {
		original.call(instance, attacker);
		attackers.add(attacker);
	}

	@ModifyReturnValue(method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("RETURN"))
	private boolean slib$preventHostileTargeting(boolean original, LivingEntity target) {
		if (!attackers.contains(target) && PreventHostileTargetingEvent.EVENT.invoker().shouldNotTarget((LivingEntity) (Object) this, target)) {
			return false;
		}
		return original;
	}
}
