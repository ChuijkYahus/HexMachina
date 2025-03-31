package com.hakimen.hex_machina.common.entity.golem.goals;

import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.eval.SpecialPatterns;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import com.hakimen.hex_machina.common.entity.golem.HexGolem;
import com.hakimen.hex_machina.common.hex.envs.GolemCastingEnviorment;
import com.hakimen.hex_machina.common.registry.PatternRegister;
import com.ibm.icu.impl.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class HexGolemExecuteGoal extends Goal {

    CastingVM golemVM;
    HexGolem golem;
    ServerLevel level;

    Pair<Vec3, SpellList> old;
    Pair<Vec3, SpellList> node;
    Vec3 currentTarget;

    public HexGolemExecuteGoal(HexGolem golem) {
        this.golem = golem;
        this.level = (ServerLevel) this.golem.level();
        golemVM = CastingVM.empty(new GolemCastingEnviorment(level, golem, this));
        this.golem.setPathfindingMalus(BlockPathTypes.WATER, -10);
        this.golem.setPathfindingMalus(BlockPathTypes.WALKABLE, 10);
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

        if (node != null) {
            currentTarget = node.first;
            double dist = Double.MAX_VALUE;
            if (this.currentTarget != null) {
                dist =  this.getGolem().getOnPos().getCenter().distanceTo(currentTarget);
                this.getGolem().getNavigation().moveTo(currentTarget.x, currentTarget.y, currentTarget.z , 1.5);
            }


            if (currentTarget != null && dist <= 3) {
                List<Iota> iotas = new ArrayList<>();
                for (int i = 0; i < node.second.size(); i++) {
                    iotas.add(node.second.getAt(i));
                }
                old = node;
                var view = golemVM.queueExecuteAndWrapIotas(iotas, getLevel());
                if (old == node) {
                    node = null;
                    old = null;
                }
                currentTarget = null;
            }
        } else {
            if(getGolem().getGolemHex()  != null && !getGolem().getGolemHex().isEmpty()) {
                golemVM.queueExecuteAndWrapIotas(getGolem().getGolemHex(), getLevel());
            }
        }
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

    public Pair<Vec3, SpellList> getNode() {
        return node;
    }

    public HexGolemExecuteGoal setNode(Pair<Vec3, SpellList> node) {
        this.node = node;
        return this;
    }
}
