/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.client.outlineentity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import moriyashiine.strawberrylib.impl.client.event.OutlineDataAttachment;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@ModifyReturnValue(method = "hasOutline", at = @At("RETURN"))
	private boolean slib$outlineEntity(boolean original, Entity entity) {
		@Nullable OutlineEntityEvent.OutlineData outlineData = ((OutlineDataAttachment) entity).slib$getOutlineData();
		if (outlineData != null && outlineData.state() != TriState.DEFAULT) {
			return outlineData.state().get();
		}
		return original;
	}
}
