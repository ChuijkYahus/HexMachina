package com.hakimen.hex_machina.common.actions.golem;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.hakimen.hex_machina.common.entity.golem.HexGolem;
import com.hakimen.hex_machina.common.hex.envs.GolemCastingEnviorment;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GolemMoveAction extends GolemBasedAction{

    public static final GolemMoveAction INSTANCE = new GolemMoveAction();

    @Override
    public long getMediaCost() {
        return 0;
    }

    @Override
    public int getArgc() {
        return 1;
    }

    @Override
    List<Iota> execInEnvironment(@NotNull List<? extends Iota> list, @NotNull GolemCastingEnviorment castingEnvironment) {

        Vec3 pos = OperatorUtils.getVec3(list,0,getArgc());

        castingEnvironment.getGolem().getNavigation().moveTo(pos.x,pos.y,pos.z, 1);
        castingEnvironment.getGoal().setTargetPos(pos);

        return List.of();
    }
}
