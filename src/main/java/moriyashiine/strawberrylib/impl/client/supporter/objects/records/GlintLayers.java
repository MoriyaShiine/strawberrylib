/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.client.supporter.objects.records;

import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.*;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public record GlintLayers(RenderType armorEntityGlint, RenderType glintTranslucent, RenderType glint, RenderType entityGlint) {
	private static final Map<GlintColor, GlintLayers> LAYERS = new HashMap<>();

	static {
		for (GlintColor color : GlintColor.values()) {
			if (color == GlintColor.PURPLE) {
				LAYERS.put(color, new GlintLayers(RenderTypes.armorEntityGlint(), RenderTypes.glintTranslucent(), RenderTypes.glint(), RenderTypes.entityGlint()));
			} else {
				LAYERS.put(color, of(color));
			}
		}
	}

	public static GlintLayers currentLayer = null;

	public static GlintLayers getLayers(GlintColor color) {
		return LAYERS.get(color);
	}

	private static GlintLayers of(GlintColor color) {
		Identifier armorId = StrawberryLib.id("textures/misc/enchanted_glint_armor_" + color.getName() + ".png");
		Identifier itemId = StrawberryLib.id("textures/misc/enchanted_glint_item_" + color.getName() + ".png");

		RenderType armorEntityGlint = RenderType.create(
				"armor_entity_glint_" + color.getName(),
				RenderSetup.builder(RenderPipelines.GLINT)
						.withTexture("Sampler0", armorId)
						.setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
						.setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
						.createRenderSetup()
		);
		RenderType glintTranslucent = RenderType.create(
				"glint_translucent_" + color.getName(),
				RenderSetup.builder(RenderPipelines.GLINT)
						.withTexture("Sampler0", itemId)
						.setTextureTransform(TextureTransform.GLINT_TEXTURING)
						.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
						.createRenderSetup()
		);
		RenderType glint = RenderType.create(
				"glint_" + color.getName(),
				RenderSetup.builder(RenderPipelines.GLINT)
						.withTexture("Sampler0", itemId)
						.setTextureTransform(TextureTransform.GLINT_TEXTURING)
						.createRenderSetup()
		);
		RenderType entityGlint = RenderType.create(
				"entity_glint_" + color.getName(),
				RenderSetup.builder(RenderPipelines.GLINT)
						.withTexture("Sampler0", itemId)
						.setTextureTransform(TextureTransform.ENTITY_GLINT_TEXTURING)
						.createRenderSetup()
		);
		return new GlintLayers(armorEntityGlint, glintTranslucent, glint, entityGlint);
	}
}
