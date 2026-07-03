package moriyashiine.strawberrylib.impl.common.supporter;

import dev.upcraft.datasync.api.util.Entitlements;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataType;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.supporter.payload.SyncSupporterValuePayload;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class SupporterInit {
	private static final Identifier SUPPORTER_KEY = StrawberryLib.id("supporter");

	public static final ResourceKey<Registry<SupporterDataType<?>>> SUPPORTER_DATA_TYPE_KEY = ResourceKey.createRegistryKey(StrawberryLib.id("supporter"));
	public static final Registry<SupporterDataType<?>> SUPPORTER_DATA_TYPE = FabricRegistryBuilder.create(SUPPORTER_DATA_TYPE_KEY).buildAndRegister();

	public static final SupporterDataType<GlintColor> EQUIPPABLE_GLINT_COLOR = SLibSupporterUtils.registerDataType(StrawberryLib.id("equippable_glint_color"), GlintColor.CODEC, GlintColor.STREAM_CODEC, GlintColor.PURPLE);
	public static final SupporterDataType<GlintColor> GLINT_COLOR = SLibSupporterUtils.registerDataType(StrawberryLib.id("glint_color"), GlintColor.CODEC, GlintColor.STREAM_CODEC, GlintColor.PURPLE);

	public static void init() {
		initPayloads();
	}

	public static boolean isSupporter(Player player) {
		return isSupporter(player.getUUID());
	}

	public static boolean isSupporter(UUID uuid) {
		return FabricLoader.getInstance().isDevelopmentEnvironment() || Entitlements.getOrEmpty(uuid).keys().contains(SUPPORTER_KEY);
	}

	private static void initPayloads() {
		// server payloads
		PayloadTypeRegistry.serverboundPlay().register(SyncSupporterValuePayload.TYPE, SyncSupporterValuePayload.CODEC);
		// server receivers
		ServerPlayNetworking.registerGlobalReceiver(SyncSupporterValuePayload.TYPE, new SyncSupporterValuePayload.Receiver());
	}
}
