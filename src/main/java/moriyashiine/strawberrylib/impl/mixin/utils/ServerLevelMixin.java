/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.utils;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
	@ModifyReturnValue(method = "isPvpAllowed", at = @At("RETURN"))
	private boolean nycto$bypassPvp(boolean original) {
		return original || StrawberryLib.bypassPvpAllowed;
	}
}