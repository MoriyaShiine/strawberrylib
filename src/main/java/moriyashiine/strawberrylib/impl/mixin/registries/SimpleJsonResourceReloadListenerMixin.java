/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.registries;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SimpleJsonResourceReloadListener.class)
public class SimpleJsonResourceReloadListenerMixin {
	@WrapWithCondition(method = "lambda$scanDirectory$1", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;error(Ljava/lang/String;[Ljava/lang/Object;)V"))
	private static boolean slib$scanErrorless(Logger instance, String s, Object[] objects) {
		return !StrawberryLib.scanErrorless;
	}
}
