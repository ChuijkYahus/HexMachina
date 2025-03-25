package com.hakimen.hex_machina.common.entity;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.eval.SpecialPatterns;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.iota.*;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import at.petrak.hexcasting.api.utils.NBTHelper;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import at.petrak.hexcasting.common.msgs.MsgNewSpiralPatternsS2C;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.hakimen.hex_machina.common.hex.BulletProjectileEnv;
import com.hakimen.hex_machina.common.items.HexGunItem;
import com.hakimen.hex_machina.common.registry.EntityRegister;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BulletProjectile extends ThrowableProjectile {

    public static final String BULLET_DATA = "Bullet";
    public static final String BLOCK_CAST = "BlockCast";
    public static final String PLAYER = "Player";
    public static final String GUN = "Gun";


    List<Iota> iotasToCast;
    ItemStack gun;
    UUID player;
    boolean isBlockCast;

    public BulletProjectile(Level level) {
        super(EntityRegister.BULLET_PROJECTILE.get(), level);
        iotasToCast = new ArrayList<>();
        isBlockCast = false;
    }

    public BulletProjectile(Level level, List<Iota> iotasToCast, boolean isBlockCast, ItemStack gun, UUID player) {
        super(EntityRegister.BULLET_PROJECTILE.get(), level);
        this.iotasToCast = iotasToCast;
        this.isBlockCast = isBlockCast;
        this.gun = gun;
        this.player = player;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        var patsTag = NBTHelper.getList(compoundTag,BULLET_DATA, Tag.TAG_COMPOUND);

        if (patsTag != null) {
            var out = new ArrayList<Iota>();
            for (var patTag : patsTag) {
                CompoundTag tag = NBTHelper.getAsCompound(patTag);
                out.add(IotaType.deserialize(tag, (ServerLevel) level()));
            }
            iotasToCast = out;
        }

        if(NBTHelper.hasUUID(compoundTag, PLAYER)) {
            player = NBTHelper.getUUID(compoundTag, PLAYER);
        }
        if(NBTHelper.hasCompound(compoundTag,GUN)){
            gun = ItemStack.of(NBTHelper.getCompound(compoundTag, GUN));
        }
        isBlockCast = NBTHelper.getBoolean(compoundTag,BLOCK_CAST);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        ListTag list = new ListTag();

        if(iotasToCast != null){
            for (var iota : iotasToCast) {
                CompoundTag tag = IotaType.serialize(iota);
                list.add(tag);
            }
        }

        if(player != null){
            NBTHelper.putUUID(compoundTag, PLAYER, player);
        }

        NBTHelper.putList(compoundTag, BULLET_DATA, list);

        if(gun != null){
            NBTHelper.putCompound(compoundTag, GUN, gun.save(new CompoundTag()));
        }

        NBTHelper.putBoolean(compoundTag,BLOCK_CAST, isBlockCast);
    }


    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if(!this.level().isClientSide && !isBlockCast && entityHitResult.getType() == HitResult.Type.ENTITY){
            castIota(List.of(
                    new PatternIota(SpecialPatterns.CONSIDERATION),
                    new EntityIota(entityHitResult.getEntity())
            ));
        }

    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if(isBlockCast && blockHitResult.getType() == HitResult.Type.BLOCK){
            castIota(List.of(
                    new PatternIota(SpecialPatterns.CONSIDERATION),
                    new Vec3Iota(blockHitResult.getBlockPos().getCenter())
            ));
        }
        if(!isBlockCast){
            castIota(List.of(
                    new PatternIota(SpecialPatterns.CONSIDERATION),
                    new NullIota()
            ));
        }
    }

    @Override
    public boolean shouldRender(double d, double e, double f) {
        return true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double d) {
        return true;
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return !isBlockCast && super.canHitEntity(entity);
    }


    @Override
    public void tick() {
        super.tick();
        this.lookAt(EntityAnchorArgument.Anchor.EYES, this.getPosition(0).add(this.getDeltaMovement().scale(4)));
    }

    public void castIota(List<Iota> extension) {

        if(iotasToCast == null) return;

        if(level().isClientSide) return;

        ServerLevel serverLevel = (ServerLevel) level();

        Player caster = serverLevel.getPlayerByUUID(player);

        if(caster == null) return;

        List<Iota> holder = iotasToCast;

        for (int i = 0; i < extension.size(); i++) {
            Iota iota = extension.get(i);
            holder.add(i, iota);
        }
        discard();

        var sPlayer = (ServerPlayer) caster;
        var ctx = new BulletProjectileEnv(sPlayer, caster.getItemInHand(InteractionHand.MAIN_HAND) == gun ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, this, gun, gun.getOrCreateTag().getInt(HexGunItem.BULLET_SLOT));
        var vm = CastingVM.empty(ctx);
        var clientView = vm.queueExecuteAndWrapIotas(holder, sPlayer.serverLevel());

        var patterns = holder.stream()
                .filter(i -> i instanceof PatternIota)
                .map(i -> ((PatternIota) i).getPattern())
                .toList();

        var packet = new MsgNewSpiralPatternsS2C(sPlayer.getUUID(), patterns, 140);
        IXplatAbstractions.INSTANCE.sendPacketToPlayer(sPlayer, packet);
        IXplatAbstractions.INSTANCE.sendPacketTracking(sPlayer, packet);

        if (clientView.getResolutionType().getSuccess()) {
            // Somehow we lost spraying particles on each new pattern, so do it here
            // this also nicely prevents particle spam on trinkets
            new ParticleSpray(caster.position(), new Vec3(0.0, 1.5, 0.0), 0.4, Math.PI / 3, 30)
                    .sprayParticles(sPlayer.serverLevel(), ctx.getPigment());
        }

        var sound = HexEvalSounds.SPELL.sound();
        if (sound != null) {
            var soundPos = this.position();
            sPlayer.level().playSound(null, soundPos.x, soundPos.y, soundPos.z,
                    sound, SoundSource.PLAYERS, 1f, 1f);
        }
    }

    public List<Iota> getIotasToCast() {
        return iotasToCast;
    }

    public BulletProjectile setIotasToCast(List<Iota> iotasToCast) {
        this.iotasToCast = iotasToCast;
        return this;
    }

    public ItemStack getGun() {
        return gun;
    }

    public BulletProjectile setGun(ItemStack gun) {
        this.gun = gun;
        return this;
    }

    public UUID getPlayer() {
        return player;
    }

    public BulletProjectile setPlayer(UUID player) {
        this.player = player;
        return this;
    }

    public boolean isBlockCast() {
        return isBlockCast;
    }

    public BulletProjectile setBlockCast(boolean blockCast) {
        isBlockCast = blockCast;
        return this;
    }
}
