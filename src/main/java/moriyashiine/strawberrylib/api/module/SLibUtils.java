/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.module;

import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import moriyashiine.strawberrylib.api.objects.enums.PayloadTarget;
import moriyashiine.strawberrylib.api.objects.enums.SubmersionGate;
import moriyashiine.strawberrylib.api.objects.records.ParticleVelocity;
import moriyashiine.strawberrylib.impl.client.payload.AddAnchoredParticlePayload;
import moriyashiine.strawberrylib.impl.client.payload.AddParticlesPayload;
import moriyashiine.strawberrylib.impl.client.payload.AddTrackingEmitterPayload;
import moriyashiine.strawberrylib.impl.client.payload.PlayAnchoredSoundPayload;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.component.entity.ModelReplacementComponent;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalFluidTags;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.Nullable;

public final class SLibUtils {
	@Nullable
	public static LivingEntity getModelReplacement(Player player) {
		return ModEntityComponents.MODEL_REPLACEMENT.get(player).getReplacement();
	}

	public static boolean hasModelReplacementType(Player player, EntityType<?> type) {
		return ModEntityComponents.MODEL_REPLACEMENT.get(player).hasReplacementType(type);
	}

	public static void addModelReplacementType(Player player, EntityType<?> type, int priority) {
		ModelReplacementComponent modelReplacementComponent = ModEntityComponents.MODEL_REPLACEMENT.get(player);
		modelReplacementComponent.addReplacementType(type, priority);
		if (!player.level().isClientSide()) {
			modelReplacementComponent.sync();
		}
	}

	public static void addModelReplacementType(Player player, EntityType<?> type) {
		addModelReplacementType(player, type, 1000);
	}

	public static void removeModelReplacementType(Player player, EntityType<?> type) {
		ModelReplacementComponent modelReplacementComponent = ModEntityComponents.MODEL_REPLACEMENT.get(player);
		modelReplacementComponent.removeReplacementType(type);
		if (!player.level().isClientSide()) {
			modelReplacementComponent.sync();
		}
	}

	public static boolean conditionallyApplyAttributeModifier(LivingEntity entity, Holder<Attribute> attribute, AttributeModifier modifier, boolean shouldHave) {
		AttributeInstance instance = entity.getAttribute(attribute);
		if (instance == null || entity.level().isClientSide()) {
			return false;
		}
		if (shouldHave) {
			if (!instance.hasModifier(modifier.id())) {
				instance.addPermanentModifier(modifier);
				return true;
			}
		} else if (instance.hasModifier(modifier.id())) {
			instance.removeModifier(modifier);
			return true;
		}
		return false;
	}


	public static boolean hasLineOfSight(Entity host, Entity target, int range) {
		if (target.level() == host.level() && host.position().distanceTo(target.position()) <= 32) {
			for (int i = -range; i <= range; i++) {
				if (host.level().clip(new ClipContext(host.position().add(0, i, 0), target.position().add(0, i, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, host)).getType() == HitResult.Type.MISS) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isAttackingPlayerCooldownWithinThreshold(float threshold) {
		return StrawberryLib.currentAttackCooldown == -1 || StrawberryLib.currentAttackCooldown >= threshold;
	}

	public static boolean isGroundedOrAirborne(LivingEntity entity, boolean allowWater) {
		if (entity instanceof Player player && player.getAbilities().flying) {
			return false;
		}
		if (!allowWater) {
			if (entity.isInWater() || entity.isSwimming()) {
				return false;
			}
		}
		return !entity.isPassenger() && !entity.isAutoSpinAttack() && !entity.isFallFlying() && !entity.onClimbable();
	}

	public static boolean isGroundedOrAirborne(LivingEntity entity) {
		return isGroundedOrAirborne(entity, false);
	}

	public static boolean isCrouching(Entity entity, boolean checkSneaking) {
		if (checkSneaking && entity.isShiftKeyDown()) {
			return true;
		}
		if (entity instanceof TamableAnimal tamableAnimal && tamableAnimal.isOrderedToSit()) {
			return true;
		}
		return entity instanceof Mob && entity.getControllingPassenger() instanceof Player player && player.isShiftKeyDown();
	}

	public static boolean isSubmerged(Entity entity, SubmersionGate gate) {
		for (int i = 0; i < Mth.ceil(entity.getBbHeight()); i++) {
			BlockState state = entity.level().getBlockState(entity.blockPosition().above(i));
			if (gate.allowsWater() && !state.is(Blocks.BUBBLE_COLUMN) && state.getFluidState().is(ConventionalFluidTags.WATER)) {
				return true;
			}
			if (gate.allowsLava() && state.getFluidState().is(ConventionalFluidTags.LAVA)) {
				return true;
			}
			if (gate.allowsPowderSnow() && state.is(Blocks.POWDER_SNOW)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isSufficientlyHigh(Entity entity, double distanceFromGround) {
		return entity.level().clip(new ClipContext(entity.position(), entity.position().add(0, -distanceFromGround, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, entity)).getType() == HitResult.Type.MISS;
	}

	public static boolean shouldHurt(Entity attacker, Entity target) {
		if (attacker == null || target == null) {
			return true;
		}
		if (attacker == target || attacker.hasPassenger(target) || target.hasPassenger(attacker)) {
			return false;
		}
		if (attacker.isAlliedTo(target) || target.isAlliedTo(attacker)) {
			return false;
		}
		if (attacker instanceof Player attackingPlayer && target instanceof Player targetPlayer) {
			return attackingPlayer.canHarmPlayer(targetPlayer);
		}
		return true;
	}

	public static boolean insertOrDrop(ServerLevel level, Entity entity, ItemStack stack) {
		if (entity instanceof Player player) {
			int emptySlot = findEmptySlot(player.getInventory());
			if (emptySlot != -1 && player.getInventory().add(emptySlot, stack)) {
				return true;
			}
		}
		entity.spawnAtLocation(level, stack);
		return false;
	}

	public static int findEmptySlot(Inventory inventory) {
		for (int i = 9; i < inventory.getNonEquipmentItems().size(); i++) {
			if (inventory.getNonEquipmentItems().get(i).isEmpty()) {
				return i;
			}
		}
		return -1;
	}

	public static void runWithPvpBypass(Runnable runnable) {
		StrawberryLib.bypassPvpAllowed = true;
		runnable.run();
		StrawberryLib.bypassPvpAllowed = false;
	}

	public static void addAnchoredParticle(Entity entity, ParticleType<?> particle, double yOffset, double speed, double intensity, PayloadTarget payloadTarget) {
		if (payloadTarget.sendsToOthers()) {
			PlayerLookup.tracking(entity).forEach(receiver -> AddAnchoredParticlePayload.send(receiver, entity, particle, yOffset, speed, intensity));
		}
		if (payloadTarget.sendsToSelf() && entity instanceof ServerPlayer player) {
			AddAnchoredParticlePayload.send(player, player, particle, yOffset, speed, intensity);
		}
	}

	public static void addAnchoredParticle(Entity entity, ParticleType<?> particle, double yOffset, double speed, double intensity) {
		addAnchoredParticle(entity, particle, yOffset, speed, intensity, PayloadTarget.ALL);
	}

	public static void addTrackingEmitter(Entity entity, ParticleType<?> particle, PayloadTarget payloadTarget) {
		if (payloadTarget.sendsToOthers()) {
			PlayerLookup.tracking(entity).forEach(receiver -> AddTrackingEmitterPayload.send(receiver, entity, particle));
		}
		if (payloadTarget.sendsToSelf() && entity instanceof ServerPlayer player) {
			AddTrackingEmitterPayload.send(player, player, particle);
		}
	}

	public static void addTrackingEmitter(Entity entity, ParticleType<?> particle) {
		addTrackingEmitter(entity, particle, PayloadTarget.ALL);
	}

	public static void addParticles(Entity entity, ParticleType<?> particle, int count, ParticleAnchor anchor, PayloadTarget payloadTarget, ParticleVelocity velocity) {
		if (payloadTarget.sendsToOthers()) {
			PlayerLookup.tracking(entity).forEach(receiver -> AddParticlesPayload.send(receiver, entity, particle, count, anchor, velocity));
		}
		if (payloadTarget.sendsToSelf() && entity instanceof ServerPlayer player) {
			AddParticlesPayload.send(player, player, particle, count, anchor, velocity);
		}
	}

	public static void addParticles(Entity entity, ParticleType<?> particle, int count, ParticleAnchor anchor, ParticleVelocity velocity) {
		addParticles(entity, particle, count, anchor, PayloadTarget.ALL, velocity);
	}

	public static void addParticles(Entity entity, ParticleType<?> particle, int count, ParticleAnchor anchor) {
		addParticles(entity, particle, count, anchor, ParticleVelocity.ZERO);
	}

	public static void playSound(Entity entity, SoundEvent sound, float volume, float pitch) {
		entity.level().playSound(null, entity.blockPosition(), sound, entity.getSoundSource(), volume, pitch);
	}

	public static void playSound(Entity entity, SoundEvent sound) {
		playSound(entity, sound, 1, 1);
	}

	public static void playAnchoredSound(Entity entity, SoundEvent sound, PayloadTarget payloadTarget) {
		if (payloadTarget.sendsToOthers()) {
			PlayerLookup.tracking(entity).forEach(receiver -> PlayAnchoredSoundPayload.send(receiver, entity, sound));
		}
		if (payloadTarget.sendsToSelf() && entity instanceof ServerPlayer player) {
			PlayAnchoredSoundPayload.send(player, player, sound);
		}
	}

	public static void playAnchoredSound(Entity entity, SoundEvent sound) {
		playAnchoredSound(entity, sound, PayloadTarget.ALL);
	}
}
