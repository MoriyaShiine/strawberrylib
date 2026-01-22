/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.item.GlintLayersRenderState;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {
	@Inject(method = "appendItemLayers", at = @At("TAIL"))
	private void slib$supporterGlint(ItemStackRenderState output, ItemStack item, ItemDisplayContext displayContext, Level level, ItemOwner owner, int seed, CallbackInfo ci) {
		GlintLayersRenderState glintLayersRenderState = new GlintLayersRenderState();
		if (owner != null && owner.asLivingEntity() instanceof Player player && SLibSupporterUtils.isSupporter(player)) {
			glintLayersRenderState.glintLayers = GlintLayers.getLayers(SLibSupporterUtils.getData(player, item.has(DataComponents.EQUIPPABLE) ? SupporterInit.EQUIPPABLE_GLINT_COLOR : SupporterInit.GLINT_COLOR));
		}
		output.setData(GlintLayersRenderState.KEY, glintLayersRenderState);
	}
}
