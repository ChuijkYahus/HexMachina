package com.hakimen.hex_machina.common.entity.golem.goals;

import at.petrak.hexcasting.api.casting.eval.SpecialPatterns;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import com.hakimen.hex_machina.common.entity.golem.HexGolem;
import com.hakimen.hex_machina.common.hex.envs.GolemCastingEnviorment;
import com.hakimen.hex_machina.common.registry.PatternRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class HexGolemExecuteGoal extends Goal {

    CastingVM golemVM;
    HexGolem golem;
    ServerLevel level;

    Vec3 targetPos;

    List<Iota> onDestination;

    public HexGolemExecuteGoal(HexGolem golem) {
        this.golem = golem;
        this.level = (ServerLevel) this.golem.level();
        golemVM = CastingVM.empty(new GolemCastingEnviorment(level, golem, this));
        onDestination = new ArrayList<>();

    }

    @Override
    public EnumSet<Flag> getFlags() {
        return EnumSet.allOf(Flag.class);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        golemVM.getImage().getStack().clear();
        var clientView = golemVM.queueExecuteAndWrapIotas(new ArrayList<>(List.of(
                new PatternIota(SpecialPatterns.CONSIDERATION),
                new Vec3Iota(level.getRandomPlayer().getPosition(0)),
                new PatternIota(PatternRegister.MOVE_GOLEM.get().prototype()),
                new PatternIota(SpecialPatterns.CONSIDERATION),
                new DoubleIota(1.5),
                new PatternIota(PatternRegister.IS_GOLEM_IN_RANGE_OF_TARGET.get().prototype())
        )), level);
    }


    @Override
    public boolean canContinueToUse() {
        return true;
    }

    public CastingVM getGolemVM() {
        return golemVM;
    }

    public HexGolem getGolem() {
        return golem;
    }

    public ServerLevel getLevel() {
        return level;
    }

    public List<Iota> getOnDestination() {
        return onDestination;
    }

    public HexGolemExecuteGoal setOnDestination(List<Iota> onDestination) {
        this.onDestination = onDestination;
        return this;
    }

    public Vec3 getTargetPos() {
        return targetPos;
    }

    public HexGolemExecuteGoal setTargetPos(Vec3 targetPos) {
        this.targetPos = targetPos;
        return this;
    }
}
