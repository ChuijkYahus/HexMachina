package com.hakimen.hex_machina.common.actions.gun;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.EntityIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.hakimen.hex_machina.common.hex.BulletProjectileEnv;
import com.hakimen.hex_machina.common.hex.mishaps.MishapNotBullet;
import com.hakimen.hex_machina.common.hex.mishaps.MishapNotGun;
import com.hakimen.hex_machina.common.items.HexGunItem;
import com.hakimen.hex_machina.common.utils.exceptions.ExceptionUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BulletReflectionAction implements ConstMediaAction {
    public static final BulletReflectionAction INSTANCE = new BulletReflectionAction();


    @Override
    public int getArgc() {
        return 0;
    }

    @Override
    public long getMediaCost() {
        return 0;
    }

    @NotNull
    @Override
    public List<Iota> execute(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) {
        if(castingEnvironment instanceof BulletProjectileEnv bulletEnv){
            return List.of(new EntityIota(bulletEnv.getBulletProjectile()));
        }else{
            ExceptionUtils.throwException(new MishapNotBullet());
        }
        return List.of();
    }

    @NotNull
    @Override
    public CostMediaActionResult executeWithOpCount(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) {
        return DefaultImpls.executeWithOpCount(this, list, castingEnvironment);
    }

    @NotNull
    @Override
    public OperationResult operate(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage, @NotNull SpellContinuation spellContinuation) {

        return DefaultImpls.operate(this,castingEnvironment,castingImage,spellContinuation);
    }
}