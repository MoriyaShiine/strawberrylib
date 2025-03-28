/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.client.replacehearttextures;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.strawberrylib.api.event.client.ReplaceHeartTexturesEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Shadow
	@Nullable
	protected abstract PlayerEntity getCameraPlayer();

	@ModifyExpressionValue(method = "drawHeart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud$HeartType;getTexture(ZZZ)Lnet/minecraft/util/Identifier;"))
	private Identifier slib$replaceHeartTextures(Identifier original, DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half) {
		if (type != InGameHud.HeartType.CONTAINER && type != InGameHud.HeartType.ABSORBING) {
			Optional<ReplaceHeartTexturesEvent.TextureSet> textureSet = ReplaceHeartTexturesEvent.EVENT.invoker().getTextureSet(getCameraPlayer());
			if (textureSet.isPresent()) {
				ReplaceHeartTexturesEvent.TextureSet set = textureSet.get();
				if (!hardcore) {
					if (half) {
						return blinking ? set.halfBlinkingTexture() : set.halfTexture();
					} else {
						return blinking ? set.fullBlinkingTexture() : set.fullTexture();
					}
				} else if (half) {
					return blinking ? set.hardcoreHalfBlinkingTexture() : set.hardcoreHalfTexture();
				} else {
					return blinking ? set.hardcoreFullBlinkingTexture() : set.hardcoreFullTexture();
				}
			}
		}
		return original;
	}
}
