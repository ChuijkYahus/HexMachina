package com.hakimen.hex_machina.common.hex;

import at.petrak.hexcasting.api.casting.eval.CastResult;
import at.petrak.hexcasting.api.casting.eval.env.PlayerBasedCastEnv;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import at.petrak.hexcasting.common.msgs.MsgNewSpiralPatternsS2C;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.hakimen.hex_machina.common.entity.BulletProjectile;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class BulletProjectileEnv extends PlayerBasedCastEnv {

    BulletProjectile bulletProjectile;
    ItemStack gun;
    int slot;

    public BulletProjectileEnv(ServerPlayer caster, InteractionHand hand, BulletProjectile bullet, ItemStack gun, int slot) {
        super(caster, hand);
        this.bulletProjectile = bullet;
        this.gun = gun;
        this.slot = slot;
    }

    @Override
    public void postExecution(CastResult result) {
        super.postExecution(result);

        if (result.component1() instanceof PatternIota patternIota) {
            var packet = new MsgNewSpiralPatternsS2C(
                    this.caster.getUUID(), List.of(patternIota.getPattern()), 140
            );
            IXplatAbstractions.INSTANCE.sendPacketToPlayer(this.caster, packet);
            IXplatAbstractions.INSTANCE.sendPacketTracking(this.caster, packet);
        }

    }
    @Override
    protected long extractMediaEnvironment(long cost, boolean simulate) {
        if (this.caster.isCreative())
            return 0;

        if (cost > 0) {
            cost = this.extractMediaFromInventory(cost, this.canOvercast(), simulate);
        }

        return cost;
    }


    @Override
    public InteractionHand getCastingHand() {
        return castingHand;
    }

    @Override
    protected List<ItemStack> getUsableStacks(StackDiscoveryMode mode) {

        ArrayList<ItemStack> stack = new ArrayList<>(super.getUsableStacks(mode));

        stack.add(gun);

        return stack;
    }

    @Override
    public boolean isVecInRangeEnvironment(Vec3 vec) {
        //Ambit
        return super.isVecInRangeEnvironment(vec) || bulletProjectile.getPosition(0).distanceTo(vec) <= 8;
    }

    @Override
    public FrozenPigment getPigment() {
        var casterStack = this.caster.getItemInHand(this.castingHand);
        var casterHexHolder = IXplatAbstractions.INSTANCE.findHexHolder(casterStack);
        if (casterHexHolder == null)
            return IXplatAbstractions.INSTANCE.getPigment(this.caster);
        var hexHolderPigment = casterHexHolder.getPigment();
        if (hexHolderPigment != null)
            return hexHolderPigment;
        return IXplatAbstractions.INSTANCE.getPigment(this.caster);
    }

    public BulletProjectile getBulletProjectile() {
        return bulletProjectile;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getGun() {
        return gun;
    }
}