/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.preventhostiletargeting;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import net.minecraft.world.entity.LivingEntity;
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
		attackers.removeIf(attacker -> attacker == null || !attacker.canBeSeenAsEnemy());
	}

	@WrapOperation(method = "resolveMobResponsibleForDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setLastHurtByMob(Lnet/minecraft/world/entity/LivingEntity;)V"))
	private void slib$preventHostileTargeting(LivingEntity instance, LivingEntity hurtBy, Operation<Void> original) {
		original.call(instance, hurtBy);
		attackers.add(hurtBy);
	}

	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("RETURN"))
	private boolean slib$preventHostileTargeting(boolean original, LivingEntity target) {
		if (!attackers.contains(target) && PreventHostileTargetingEvent.EVENT.invoker().preventsTargeting((LivingEntity) (Object) this, target).get()) {
			return false;
		}
		return original;
	}
}
