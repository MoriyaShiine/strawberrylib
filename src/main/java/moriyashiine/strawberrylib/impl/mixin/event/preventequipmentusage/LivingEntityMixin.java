/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "isEquippableInSlot", at = @At("RETURN"))
	private boolean slib$preventEquipmentUsage(boolean original, ItemStack itemStack, EquipmentSlot slot) {
		return original && !PreventEquipmentUsageEvent.cannotEquip((LivingEntity) (Object) this, itemStack, slot);
	}

	@SuppressWarnings("ConstantValue")
	@ModifyExpressionValue(method = "canEquipWithDispenser", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;canUseSlot(Lnet/minecraft/world/entity/EquipmentSlot;)Z"))
	private boolean slib$preventEquipmentUsageDispenser(boolean original, ItemStack itemStack, @Local(name = "slot") EquipmentSlot slot) {
		return original && !PreventEquipmentUsageEvent.cannotEquip((LivingEntity) (Object) this, itemStack, slot);
	}

	@Inject(method = "dropEquipment", at = @At("TAIL"))
	private void slib$preventEquipmentUsage(ServerLevel level, CallbackInfo ci) {
		if (!level.getGameRules().get(GameRules.KEEP_INVENTORY)) {
			ModEntityComponents.STORED_EQUIPMENT.get(this).dropStoredEquipment();
		}
	}
}
