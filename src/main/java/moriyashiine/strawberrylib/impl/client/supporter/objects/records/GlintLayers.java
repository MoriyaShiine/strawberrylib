/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.supporter.objects.records;

import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public record GlintLayers(RenderLayer armorEntityGlint, RenderLayer glintTranslucent, RenderLayer glint,
						  RenderLayer entityGlint) {
	private static final Map<GlintColor, GlintLayers> LAYERS = new HashMap<>();

	static {
		for (GlintColor color : GlintColor.values()) {
			if (color == GlintColor.PURPLE) {
				LAYERS.put(color, new GlintLayers(RenderLayers.armorEntityGlint(), RenderLayers.glintTranslucent(), RenderLayers.glint(), RenderLayers.entityGlint()));
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
		Identifier entityName = StrawberryLib.id("textures/misc/enchanted_glint_armor_" + color.getName() + ".png");
		Identifier itemName = StrawberryLib.id("textures/misc/enchanted_glint_item_" + color.getName() + ".png");

		RenderLayer armorEntityGlint = RenderLayer.of(
				"armor_entity_glint_" + color.getName(),
				RenderSetup.builder(RenderPipelines.GLINT)
						.texture("Sampler0", entityName)
						.textureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
						.layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
						.build()
		);
		RenderLayer glintTranslucent = RenderLayer.of(
				"glint_translucent_" + color.getName(),
				RenderSetup.builder(RenderPipelines.GLINT)
						.texture("Sampler0", itemName)
						.textureTransform(TextureTransform.GLINT_TEXTURING)
						.outputTarget(OutputTarget.ITEM_ENTITY_TARGET)
						.build()
		);
		RenderLayer glint = RenderLayer.of(
				"glint_" + color.getName(),
				RenderSetup.builder(RenderPipelines.GLINT)
						.texture("Sampler0", itemName)
						.textureTransform(TextureTransform.GLINT_TEXTURING)
						.build()
		);
		RenderLayer entityGlint = RenderLayer.of(
				"entity_glint_" + color.getName(),
				RenderSetup.builder(RenderPipelines.GLINT)
						.texture("Sampler0", itemName)
						.textureTransform(TextureTransform.ENTITY_GLINT_TEXTURING)
						.build()
		);
		return new GlintLayers(armorEntityGlint, glintTranslucent, glint, entityGlint);
	}
}
