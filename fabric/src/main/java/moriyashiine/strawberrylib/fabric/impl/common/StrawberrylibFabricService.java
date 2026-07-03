package moriyashiine.strawberrylib.fabric.impl.common;

import com.google.auto.service.AutoService;
import moriyashiine.strawberrylib.fabric.impl.mixin.modelreplacement.EntityAccessor;
import moriyashiine.strawberrylib.impl.common.StrawberryLibService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

@AutoService(StrawberryLibService.class)
public class StrawberrylibFabricService implements StrawberryLibService {
	@Override
	public void playMuffledStepSound(Entity entity, BlockState state, BlockPos pos) {
		((EntityAccessor) entity).slib$playMuffledStepSound(state);
	}
}
