/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.module;

import moriyashiine.strawberrylib.api.objects.enums.PacketTarget;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import moriyashiine.strawberrylib.api.objects.enums.SubmersionGate;
import moriyashiine.strawberrylib.api.objects.records.ParticleVelocity;
import moriyashiine.strawberrylib.impl.client.payload.AddAnchoredParticlePayload;
import moriyashiine.strawberrylib.impl.client.payload.AddEmitterParticlePayload;
import moriyashiine.strawberrylib.impl.client.payload.AddParticlesPayload;
import moriyashiine.strawberrylib.impl.client.payload.PlayAnchoredSoundPayload;
import moriyashiine.strawberrylib.impl.common.component.entity.ModelReplacementComponent;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalFluidTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import org.jetbrains.annotations.Nullable;

public final class SLibUtils {
	@Nullable
	public static LivingEntity getModelReplacement(PlayerEntity player) {
		return ModEntityComponents.MODEL_REPLACEMENT.get(player).getReplacement();
	}

	public static void setModelReplacement(PlayerEntity player, @Nullable EntityType<?> entityType, boolean sync) {
		ModelReplacementComponent modelReplacementComponent = ModEntityComponents.MODEL_REPLACEMENT.get(player);
		modelReplacementComponent.setReplacementType(entityType);
		if (sync) {
			modelReplacementComponent.sync();
		}
	}

	public static void setModelReplacement(PlayerEntity player, @Nullable EntityType<?> entityType) {
		setModelReplacement(player, entityType, true);
	}

	public static boolean conditionallyApplyAttributeModifier(LivingEntity entity, RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier, boolean shouldHave) {
		EntityAttributeInstance instance = entity.getAttributeInstance(attribute);
		if (instance == null || entity.getWorld().isClient) {
			return false;
		}
		if (shouldHave) {
			if (!instance.hasModifier(modifier.id())) {
				instance.addPersistentModifier(modifier);
				return true;
			}
		} else if (instance.hasModifier(modifier.id())) {
			instance.removeModifier(modifier);
			return true;
		}
		return false;
	}


	public static boolean canSee(Entity host, Entity target, int range) {
		if (target.getWorld() == host.getWorld() && host.getPos().distanceTo(target.getPos()) <= 32) {
			for (int i = -range; i <= range; i++) {
				if (host.getWorld().raycast(new RaycastContext(host.getPos().add(0, i, 0), target.getPos().add(0, i, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, host)).getType() == HitResult.Type.MISS) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isGroundedOrAirborne(LivingEntity living, boolean allowWater) {
		if (living instanceof PlayerEntity player && player.getAbilities().flying) {
			return false;
		}
		if (!allowWater) {
			if (living.isTouchingWater() || living.isSwimming()) {
				return false;
			}
		}
		return !living.isGliding() && !living.hasVehicle() && !living.isClimbing();
	}

	public static boolean isGroundedOrAirborne(LivingEntity living) {
		return isGroundedOrAirborne(living, false);
	}

	public static boolean isCrouching(Entity entity, boolean checkSneaking) {
		if (checkSneaking && entity.isSneaking()) {
			return true;
		}
		if (entity instanceof TameableEntity tameable && tameable.isSitting()) {
			return true;
		}
		return entity instanceof MobEntity && entity.getControllingPassenger() instanceof PlayerEntity player && player.isSneaking();
	}

	public static boolean isSubmerged(Entity entity, SubmersionGate gate) {
		for (int i = 0; i < MathHelper.ceil(entity.getHeight()); i++) {
			BlockState blockState = entity.getWorld().getBlockState(entity.getBlockPos().up(i));
			if (gate.allowsWater() && !blockState.isOf(Blocks.BUBBLE_COLUMN) && blockState.getFluidState().isIn(ConventionalFluidTags.WATER)) {
				return true;
			}
			if (gate.allowsLava() && blockState.getFluidState().isIn(ConventionalFluidTags.LAVA)) {
				return true;
			}
			if (gate.allowsPowderSnow() && blockState.isOf(Blocks.POWDER_SNOW)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isSufficientlyHigh(Entity entity, double distanceFromGround) {
		return entity.getWorld().raycast(new RaycastContext(entity.getPos(), entity.getPos().add(0, -distanceFromGround, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, entity)).getType() == HitResult.Type.MISS;
	}

	public static boolean shouldHurt(Entity attacker, Entity target) {
		if (attacker == null || target == null) {
			return true;
		}
		if (attacker == target || attacker.hasPassenger(target) || target.hasPassenger(attacker)) {
			return false;
		}
		if (attacker.isTeammate(target) || target.isTeammate(attacker)) {
			return false;
		}
		if (attacker instanceof PlayerEntity attackingPlayer && target instanceof PlayerEntity targetPlayer) {
			return attackingPlayer.shouldDamagePlayer(targetPlayer);
		}
		return true;
	}

	public static void addAnchoredParticle(Entity entity, ParticleType<?> particleType, double yOffset, double speed, double intensity, PacketTarget packetTarget) {
		if (packetTarget.sendsToOthers()) {
			PlayerLookup.tracking(entity).forEach(receiver -> AddAnchoredParticlePayload.send(receiver, entity, particleType, yOffset, speed, intensity));
		}
		if (packetTarget.sendsToSelf() && entity instanceof ServerPlayerEntity player) {
			AddAnchoredParticlePayload.send(player, player, particleType, yOffset, speed, intensity);
		}
	}

	public static void addAnchoredParticle(Entity entity, ParticleType<?> particleType, double yOffset, double speed, double intensity) {
		addAnchoredParticle(entity, particleType, yOffset, speed, intensity, PacketTarget.ALL);
	}

	public static void addEmitterParticle(Entity entity, ParticleType<?> particleType, PacketTarget packetTarget) {
		if (packetTarget.sendsToOthers()) {
			PlayerLookup.tracking(entity).forEach(receiver -> AddEmitterParticlePayload.send(receiver, entity, particleType));
		}
		if (packetTarget.sendsToSelf() && entity instanceof ServerPlayerEntity player) {
			AddEmitterParticlePayload.send(player, player, particleType);
		}
	}

	public static void addEmitterParticle(Entity entity, ParticleType<?> particleType) {
		addEmitterParticle(entity, particleType, PacketTarget.ALL);
	}

	public static void addParticles(Entity entity, ParticleType<?> particleType, int count, ParticleAnchor anchor, PacketTarget packetTarget, ParticleVelocity velocity) {
		if (packetTarget.sendsToOthers()) {
			PlayerLookup.tracking(entity).forEach(receiver -> AddParticlesPayload.send(receiver, entity, particleType, count, anchor, velocity));
		}
		if (packetTarget.sendsToSelf() && entity instanceof ServerPlayerEntity player) {
			AddParticlesPayload.send(player, player, particleType, count, anchor, velocity);
		}
	}

	public static void addParticles(Entity entity, ParticleType<?> particleType, int count, ParticleAnchor anchor) {
		addParticles(entity, particleType, count, anchor, PacketTarget.ALL, ParticleVelocity.ZERO);
	}

	public static void playSound(Entity entity, SoundEvent soundEvent, float volume, float pitch) {
		entity.getWorld().playSound(null, entity.getBlockPos(), soundEvent, entity.getSoundCategory(), volume, pitch);
	}

	public static void playSound(Entity entity, SoundEvent soundEvent) {
		playSound(entity, soundEvent, 1, 1);
	}

	public static void playAnchoredSound(Entity entity, SoundEvent soundEvent, PacketTarget packetTarget) {
		if (packetTarget.sendsToOthers()) {
			PlayerLookup.tracking(entity).forEach(receiver -> PlayAnchoredSoundPayload.send(receiver, entity, soundEvent));
		}
		if (packetTarget.sendsToSelf() && entity instanceof ServerPlayerEntity player) {
			PlayAnchoredSoundPayload.send(player, player, soundEvent);
		}
	}

	public static void playAnchoredSound(Entity entity, SoundEvent soundEvent) {
		playAnchoredSound(entity, soundEvent, PacketTarget.ALL);
	}
}
