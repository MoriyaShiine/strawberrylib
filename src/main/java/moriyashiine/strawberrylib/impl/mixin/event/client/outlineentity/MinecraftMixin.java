/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.client.outlineentity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import moriyashiine.strawberrylib.impl.client.event.OutlineDataAttachment;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@ModifyReturnValue(method = "shouldEntityAppearGlowing", at = @At("RETURN"))
	private boolean slib$outlineEntity(boolean original, Entity entity) {
		OutlineEntityEvent.@Nullable OutlineData outlineData = ((OutlineDataAttachment) entity).slib$getOutlineData();
		if (outlineData != null && outlineData.state() != TriState.DEFAULT) {
			return outlineData.state().get();
		}
		return original;
	}
}
