/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.client.replacehearttextures;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.strawberrylib.api.event.client.ReplaceHeartTexturesEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public class GuiMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@ModifyExpressionValue(method = "extractHeart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui$HeartType;getSprite(ZZZ)Lnet/minecraft/resources/Identifier;"))
	private Identifier slib$replaceHeartTextures(Identifier original, GuiGraphicsExtractor graphics, Gui.HeartType type, int xo, int yo, boolean isHardcore, boolean blinks, boolean half) {
		if (type != Gui.HeartType.CONTAINER && type != Gui.HeartType.ABSORBING) {
			Player player = minecraft.player;
			if (player != null) {
				ReplaceHeartTexturesEvent.@Nullable TextureSet textureSet = ReplaceHeartTexturesEvent.EVENT.invoker().getTextureSet(player);
				if (textureSet != null) {
					if (!isHardcore) {
						if (half) {
							return blinks ? textureSet.halfBlinkingTexture() : textureSet.halfTexture();
						} else {
							return blinks ? textureSet.fullBlinkingTexture() : textureSet.fullTexture();
						}
					} else if (half) {
						return blinks ? textureSet.hardcoreHalfBlinkingTexture() : textureSet.hardcoreHalfTexture();
					} else {
						return blinks ? textureSet.hardcoreFullBlinkingTexture() : textureSet.hardcoreFullTexture();
					}
				}
			}
		}
		return original;
	}
}
