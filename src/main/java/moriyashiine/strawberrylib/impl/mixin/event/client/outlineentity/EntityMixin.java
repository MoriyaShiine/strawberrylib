/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.client.outlineentity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(Entity.class)
public class EntityMixin {
	@ModifyReturnValue(method = "getTeamColorValue", at = @At(value = "RETURN", ordinal = 1))
	private int slib$outlineEntity(int original) {
		Optional<Integer> color = OutlineEntityEvent.OUTLINE_COLOR.invoker().getOutlineColor((Entity) (Object) this);
		if (color.isPresent()) {
			return color.get();
		}
		return original;
	}
}
