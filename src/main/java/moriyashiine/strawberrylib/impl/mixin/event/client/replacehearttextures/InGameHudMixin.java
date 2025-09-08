/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.client.replacehearttextures;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.strawberrylib.api.event.client.ReplaceHeartTexturesEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@ModifyExpressionValue(method = "drawHeart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud$HeartType;getTexture(ZZZ)Lnet/minecraft/util/Identifier;"))
	private Identifier slib$replaceHeartTextures(Identifier original, DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half) {
		if (type != InGameHud.HeartType.CONTAINER && type != InGameHud.HeartType.ABSORBING) {
			PlayerEntity player = MinecraftClient.getInstance().player;
			if (player != null) {
				@Nullable ReplaceHeartTexturesEvent.TextureSet textureSet = ReplaceHeartTexturesEvent.EVENT.invoker().getTextureSet(player);
				if (textureSet != null) {
					if (!hardcore) {
						if (half) {
							return blinking ? textureSet.halfBlinkingTexture() : textureSet.halfTexture();
						} else {
							return blinking ? textureSet.fullBlinkingTexture() : textureSet.fullTexture();
						}
					} else if (half) {
						return blinking ? textureSet.hardcoreHalfBlinkingTexture() : textureSet.hardcoreHalfTexture();
					} else {
						return blinking ? textureSet.hardcoreFullBlinkingTexture() : textureSet.hardcoreFullTexture();
					}
				}
			}
		}
		return original;
	}
}
