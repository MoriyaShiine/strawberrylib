package moriyashiine.strawberrylib.neoforge.impl.common;

import com.google.auto.service.AutoService;
import moriyashiine.strawberrylib.impl.common.StrawberryLibService;
import moriyashiine.strawberrylib.neoforge.impl.mixin.modelreplacement.EntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

@AutoService(StrawberryLibService.class)
public class StrawberrylibNeoForgeService implements StrawberryLibService {
	@Override
	public void playMuffledStepSound(Entity entity, BlockState state, BlockPos pos) {
		((EntityAccessor) entity).slib$playMuffledStepSound(state, pos);
	}
}
