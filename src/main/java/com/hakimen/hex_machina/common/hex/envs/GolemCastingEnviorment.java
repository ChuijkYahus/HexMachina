package com.hakimen.hex_machina.common.hex.envs;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.MishapEnvironment;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import com.hakimen.hex_machina.common.entity.golem.HexGolem;
import com.hakimen.hex_machina.common.entity.golem.goals.HexGolemExecuteGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class GolemCastingEnviorment extends CastingEnvironment {

    HexGolem caster;
    HexGolemExecuteGoal goal;

    public GolemCastingEnviorment(ServerLevel world, HexGolem golem, HexGolemExecuteGoal goal) {
        super(world);
        this.caster = golem;
        this.goal = goal;
    }

    @Override
    public @Nullable LivingEntity getCastingEntity() {
        return caster;
    }

    @Override
    public MishapEnvironment getMishapEnvironment() {
        return new MishapEnvironment(world, null) {

            @Override
            public void yeetHeldItemsTowards(Vec3 targetPos) {
                System.out.println("yeet");
            }

            @Override
            public void dropHeldItems() {
                System.out.println("drop_held");
            }

            @Override
            public void drown() {
                System.out.println("drown");
            }

            @Override
            public void damage(float healthProportion) {
                System.out.println("damagio");
            }

            @Override
            public void removeXp(int amount) {
                System.out.println("nuke_xp");
            }

            @Override
            public void blind(int ticks) {
                System.out.println("legaly_blind");
            }
        };
    }

    @Override
    public Vec3 mishapSprayPos() {
        return caster.getPosition(0);
    }

    @Override
    protected long extractMediaEnvironment(long cost, boolean simulate) {
        return 0;
    }

    @Override
    protected boolean isVecInRangeEnvironment(Vec3 vec) {
        return caster.getPosition(0).distanceTo(vec) < 24.00000000001;
    }

    @Override
    protected boolean hasEditPermissionsAtEnvironment(BlockPos pos) {
        return true;
    }

    @Override
    public InteractionHand getCastingHand() {
        return InteractionHand.MAIN_HAND;
    }

    @Override
    protected List<ItemStack> getUsableStacks(StackDiscoveryMode mode) {
        return List.of();
    }

    @Override
    protected List<HeldItemInfo> getPrimaryStacks() {
        return List.of();
    }

    @Override
    public boolean replaceItem(Predicate<ItemStack> stackOk, ItemStack replaceWith, @Nullable InteractionHand hand) {
        return false;
    }

    @Override
    public FrozenPigment getPigment() {
        return FrozenPigment.DEFAULT.get();
    }

    @Override
    public @Nullable FrozenPigment setPigment(@Nullable FrozenPigment pigment) {
        return null;
    }

    @Override
    public void produceParticles(ParticleSpray particles, FrozenPigment colorizer) {

    }

    @Override
    public void printMessage(Component message) {
        if(caster.level() instanceof ServerLevel lvl) {
            lvl.getRandomPlayer().sendSystemMessage(message);
        }
    }

    public HexGolemExecuteGoal getGoal() {
        return goal;
    }

    @Nullable
    public HexGolem getGolem() {
        return caster;
    }
}
