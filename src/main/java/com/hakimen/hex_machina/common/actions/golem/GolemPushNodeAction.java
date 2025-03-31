package com.hakimen.hex_machina.common.actions.golem;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.hakimen.hex_machina.common.hex.envs.GolemCastingEnviorment;
import com.ibm.icu.impl.Pair;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GolemPushNodeAction extends GolemBasedAction{

    public static final GolemPushNodeAction INSTANCE = new GolemPushNodeAction();

    @Override
    public long getMediaCost() {
        return 0;
    }

    @Override
    public int getArgc() {
        return 2;
    }

    @Override
    List<Iota> execInEnvironment(@NotNull List<? extends Iota> list, @NotNull GolemCastingEnviorment castingEnvironment) {
        Vec3 pos = OperatorUtils.getVec3(list, 0, getArgc());
        SpellList toRun = OperatorUtils.getList(list, 1, getArgc());

        Pair<Vec3, SpellList> node = Pair.of(pos, toRun );


        castingEnvironment.getGoal().setNode(node);

        return List.of();
    }

    @Override
    public @NotNull OperationResult operate(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage, @NotNull SpellContinuation spellContinuation) {
        return super.operate(castingEnvironment, castingImage, spellContinuation);
    }
}
