/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = {"isEquippableInSlot", "canEquipWithDispenser"}, at = @At("RETURN"))
	private boolean slib$preventEquipmentUsage(boolean original, ItemStack itemStack) {
		return original && !PreventEquipmentUsageEvent.EVENT.invoker().preventsUsage((LivingEntity) (Object) this, itemStack).get();
	}

	@Inject(method = "handleEquipmentChanges(Ljava/util/Map;)V", at = @At("TAIL"))
	private void slib$preventEquipmentUsage(Map<EquipmentSlot, ItemStack> changedItems, CallbackInfo ci) {
		PreventEquipmentUsageEvent.triggerEquipmentCheck((LivingEntity) (Object) this);
	}
}
