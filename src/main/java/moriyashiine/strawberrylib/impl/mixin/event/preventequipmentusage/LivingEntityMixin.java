/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = {"canEquip", "canEquipFromDispenser"}, at = @At("RETURN"))
	private boolean slib$preventEquipmentUsage(boolean original, ItemStack stack) {
		return original && !PreventEquipmentUsageEvent.EVENT.invoker().preventsUsage((LivingEntity) (Object) this, stack);
	}
}
