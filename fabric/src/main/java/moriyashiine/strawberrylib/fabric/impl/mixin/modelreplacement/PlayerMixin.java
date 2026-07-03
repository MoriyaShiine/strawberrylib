package moriyashiine.strawberrylib.fabric.impl.mixin.modelreplacement;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.common.StrawberryLibService;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin extends EntityMixin {
	@Override
	protected void slib$modelReplacementMuffledStepSound(BlockState blockState, CallbackInfo ci) {
		if (SLibUtils.getModelReplacement((Player) (Object) this) instanceof LivingEntity replacement) {
			StrawberryLibService.INSTANCE.playMuffledStepSound(replacement, blockState, replacement.blockPosition());
			ci.cancel();
		}
	}
}
