/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.module;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Optional;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public final class SLibDataUtils {
	public static final ModelTemplate CROP_CROSS = new ModelTemplate(Optional.of(StrawberryLib.id("block/crop_cross")), Optional.empty(), TextureSlot.CROP);

	public static void createCropCrossBlock(BlockModelGenerators generators, Block block, Property<Integer> property, int... stages) {
		generators.registerSimpleFlatItemModel(block.asItem());
		if (property.getPossibleValues().size() != stages.length) {
			throw new IllegalArgumentException();
		} else {
			Int2ObjectMap<Identifier> models = new Int2ObjectOpenHashMap<>();
			PropertyDispatch<MultiVariant> variant = PropertyDispatch.initial(property)
					.generate(age -> plainVariant(models.computeIfAbsent(stages[age], stage -> generators.createSuffixedVariant(block, "_stage" + stage, CROP_CROSS, TextureMapping::crop))));
			generators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(variant));
		}
	}

	public static void createGrassLikeBlock(BlockModelGenerators generators, Block block) {
		MultiVariant snowy = plainVariant(ModelLocationUtils.getModelLocation(Blocks.GRASS_BLOCK, "_snow"));
		MultiVariant plain = createRotatedVariants(plainModel(TexturedModel.CUBE_TOP_BOTTOM.get(block).updateTextures(m -> m.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.DIRT))).create(block, generators.modelOutput)));
		generators.createGrassLikeBlock(block, plain, snowy);
	}
}
