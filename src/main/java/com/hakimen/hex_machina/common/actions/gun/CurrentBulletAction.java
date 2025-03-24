package com.hakimen.hex_machina.common.actions.gun;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.misc.MediaConstants;
import com.hakimen.hex_machina.common.hex.BulletProjectileEnv;
import com.hakimen.hex_machina.common.hex.GunEnv;
import com.hakimen.hex_machina.common.hex.mishaps.MishapNotGun;
import com.hakimen.hex_machina.common.items.HexGunItem;
import com.hakimen.hex_machina.common.utils.exceptions.ExceptionUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurrentBulletAction implements ConstMediaAction {
    public static final CurrentBulletAction INSTANCE = new CurrentBulletAction();


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
        @Nullable LivingEntity caster = castingEnvironment.getCastingEntity();

        if(caster instanceof Player player){
            ItemStack stack = player.getItemInHand(castingEnvironment.getCastingHand());
            if(castingEnvironment instanceof BulletProjectileEnv bulletEnv){
                stack = bulletEnv.getGun();
            }


            if(stack.getItem() instanceof HexGunItem){
                int currentBullet = stack.getOrCreateTag().getInt(HexGunItem.BULLET_SLOT);

                if(castingEnvironment instanceof BulletProjectileEnv bulletEnv){
                    currentBullet = bulletEnv.getSlot();
                }

                return List.of(new DoubleIota(currentBullet));
            } else {
                ExceptionUtils.throwException(new MishapNotGun());
            }
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
