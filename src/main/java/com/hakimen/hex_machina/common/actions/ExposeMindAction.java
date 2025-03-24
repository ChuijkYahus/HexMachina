package com.hakimen.hex_machina.common.actions;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.misc.MediaConstants;
import com.hakimen.hex_machina.common.registry.EffectRegister;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExposeMindAction implements ConstMediaAction {
    public static final ExposeMindAction INSTANCE = new ExposeMindAction();


    @Override
    public int getArgc() {
        return 1;
    }

    @Override
    public long getMediaCost() {
        return MediaConstants.SHARD_UNIT;
    }

    @NotNull
    @Override
    public List<Iota> execute(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) {

        LivingEntity entity = OperatorUtils.getLivingEntityButNotArmorStand(list, 0, getArgc());

        if(castingEnvironment.getCastingEntity() != entity){
            entity.addEffect(new MobEffectInstance(EffectRegister.EXPOSE_MIND.get(),-1,0));
        }else{
            entity.addEffect(new MobEffectInstance(EffectRegister.EXPOSE_MIND.get(),20 * 10,0));
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

        return DefaultImpls.operate(this, castingEnvironment, castingImage, spellContinuation);
    }
}