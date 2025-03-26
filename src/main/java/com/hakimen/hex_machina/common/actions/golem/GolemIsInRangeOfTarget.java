package com.hakimen.hex_machina.common.actions.golem;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.hakimen.hex_machina.common.hex.envs.GolemCastingEnviorment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GolemIsInRangeOfTarget extends GolemBasedAction{

    public static final GolemIsInRangeOfTarget INSTANCE = new GolemIsInRangeOfTarget();

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
        double range = OperatorUtils.getDouble(list, 0, getArgc());

        if(castingEnvironment.getCastingEntity().getPosition(0).distanceTo(castingEnvironment.getGoal().getTargetPos()) < range){
            System.out.println("In range");
        }

        return List.of();
    }

    @Override
    public @NotNull OperationResult operate(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage, @NotNull SpellContinuation spellContinuation) {
        return super.operate(castingEnvironment, castingImage, spellContinuation);
    }
}
