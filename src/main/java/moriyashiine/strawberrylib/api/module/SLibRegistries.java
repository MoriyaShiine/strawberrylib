/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.module;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import moriyashiine.strawberrylib.api.objects.records.ModifierTrio;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.component.entity.ModelReplacementComponent;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.advancements.predicates.entity.EntitySubPredicate;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class SLibRegistries {
	// register helpers

	public static Holder<Attribute> registerAttribute(Identifier id, Attribute attribute) {
		return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, id, attribute);
	}

	public static Block registerBlock(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
		return Blocks.register(key, factory, properties);
	}

	public static Block registerBlock(BlockItemId id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
		return Blocks.register(id.block(), factory, properties);
	}

	public static <T extends Block> MapCodec<T> registerBlockType(String name, MapCodec<T> codec) {
		return Registry.register(BuiltInRegistries.BLOCK_TYPE, StrawberryLib.cid(name), codec);
	}

	public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String name, FabricBlockEntityTypeBuilder<T> builder) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, StrawberryLib.cid(name), builder.build());
	}

	public static <T extends ConsumeEffect> ConsumeEffect.Type<T> registerConsumeEffectType(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
		return Registry.register(BuiltInRegistries.CONSUME_EFFECT_TYPE, StrawberryLib.cid(name), new ConsumeEffect.Type<>(codec, streamCodec));
	}

	public static CreativeModeTab registerCreativeModeTab(String name, CreativeModeTab creativeModeTab) {
		return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, StrawberryLib.cid(name), creativeModeTab);
	}

	public static CreativeModeTab registerCreativeModeTab(CreativeModeTab creativeModeTab) {
		return registerCreativeModeTab(StrawberryLib.currentModId, creativeModeTab);
	}

	public static <T> DataComponentType<T> registerDataComponentType(String name, DataComponentType.Builder<T> builder) {
		return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, StrawberryLib.cid(name), builder.build());
	}

	public static <T> DataComponentType<T> registerEnchantmentEffectComponentType(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
		return Registry.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, StrawberryLib.cid(name), builderOperator.apply(DataComponentType.builder()).build());
	}

	public static <T extends EnchantmentEntityEffect> MapCodec<T> registerEnchantmentEntityEffectType(String name, MapCodec<T> codec) {
		return Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, StrawberryLib.cid(name), codec);
	}

	public static <T extends Entity> EntityType<T> registerEntityType(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key.identifier(), builder.build(key));
	}

	public static <T extends LivingEntity> EntityType<T> registerEntityType(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder, AttributeSupplier.Builder attributeBuilder) {
		EntityType<T> type = registerEntityType(key, builder);
		FabricDefaultAttributeRegistry.register(type, attributeBuilder);
		return type;
	}

	public static <T extends EntitySubPredicate> Codec<T> registerEntitySubPredicateType(String name, Codec<T> codec) {
		return Registry.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, StrawberryLib.cid(name), codec);
	}

	public static Item registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties properties) {
		Item item = factory.apply(properties.setId(key));
		if (item instanceof BlockItem blockItem) {
			blockItem.registerBlocks(Item.BY_BLOCK, item);
		}
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}

	public static Item registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> factory) {
		return registerItem(key, factory, new Item.Properties());
	}

	public static Item registerItem(ResourceKey<Item> key, Item.Properties properties) {
		return registerItem(key, Item::new, properties);
	}

	public static Item registerItem(ResourceKey<Item> key) {
		return registerItem(key, Item::new);
	}

	public static Item registerBlockItem(BlockItemId id, Block block, Item.Properties properties) {
		return registerItem(id.item(), s -> new BlockItem(block, s), properties.useBlockDescriptionPrefix());
	}

	public static Item registerBlockItem(BlockItemId id, Block block) {
		return registerBlockItem(id, block, new Item.Properties());
	}

	public static <T extends LootItemCondition> MapCodec<T> registerLootConditionType(String name, MapCodec<T> codec) {
		return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, StrawberryLib.cid(name), codec);
	}

	public static <T extends LootItemFunction> MapCodec<T> registerLootFunctionType(String name, MapCodec<T> codec) {
		return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, StrawberryLib.cid(name), codec);
	}

	public static <T extends AbstractContainerMenu> MenuType<T> registerMenuType(String name, MenuType<T> menuType) {
		return Registry.register(BuiltInRegistries.MENU, StrawberryLib.cid(name), menuType);
	}

	public static Holder<MobEffect> registerMobEffect(String name, MobEffect mobEffect) {
		return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, StrawberryLib.cid(name), mobEffect);
	}

	public static <T extends ParticleType<?>> T registerParticleType(String name, T particleType) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, StrawberryLib.cid(name), particleType);
	}

	public static Holder<Potion> registerPotion(String name, Potion potion) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, StrawberryLib.cid(name), potion);
	}

	public static <T extends Recipe<?>> RecipeSerializer<T> registerRecipeSerializer(String name, RecipeSerializer<T> recipeSerializer) {
		return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, StrawberryLib.cid(name), recipeSerializer);
	}

	public static SoundEvent registerSoundEvent(String name) {
		Identifier id = StrawberryLib.cid(name);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	public static Holder<SoundEvent> registerSoundEventHolder(String name) {
		Identifier id = StrawberryLib.cid(name);
		return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	public static <T extends CriterionTrigger<?>> T registerTrigger(String name, T trigger) {
		return Registry.register(BuiltInRegistries.TRIGGER_TYPES, StrawberryLib.cid(name), trigger);
	}

	// resources

	public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registryName, String name) {
		return ResourceKey.create(registryName, StrawberryLib.cid(name));
	}

	public static BlockItemId blockItemId(String blockName, String itemName) {
		Identifier blockId = StrawberryLib.cid(blockName);
		Identifier itemId = StrawberryLib.cid(itemName);
		return BlockItemId.create(blockId, itemId);
	}

	public static BlockItemId blockItemId(String name) {
		return blockItemId(name, name);
	}

	// misc

	public static Item.Properties editModifiers(Supplier<Item.Properties> properties, ModifierTrio... modifiers) {
		ModifierTrio.current = modifiers;
		return properties.get();
	}

	public static void scanErrorless(String prefix, Runnable runnable) {
		StrawberryLib.scanErrorless.add(prefix);
		runnable.run();
		StrawberryLib.scanErrorless.remove(prefix);
	}

	public static void registerModelReplacementCopyFunction(ModelReplacementComponent.CopyFunction copyFunction) {
		ModelReplacementComponent.COPY_FUNCTIONS.add(copyFunction);
	}
}
