package moriyashiine.strawberrylib.fabric.impl.mixin.modelreplacement;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@SuppressWarnings("CancellableInjectionUsage")
	@Inject(method = "playMuffledStepSound", at = @At("HEAD"), cancellable = true)
	protected void slib$modelReplacementMuffledStepSound(BlockState blockState, CallbackInfo ci) {
	}
}
