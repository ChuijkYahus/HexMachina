package com.hakimen.hex_machina.common.actions.golem;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import com.hakimen.hex_machina.common.hex.envs.GolemCastingEnviorment;
import com.hakimen.hex_machina.common.utils.exceptions.ExceptionUtils;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class GolemBasedAction implements ConstMediaAction {

    public abstract long getMediaCost();
    @NotNull
    @Override
    public List<Iota> execute(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) {
        if(castingEnvironment instanceof GolemCastingEnviorment golemCastingEnviorment){
            return this.execInEnvironment(list, golemCastingEnviorment);
        }else{
            ExceptionUtils.throwException(new Mishap() {
                @NotNull
                @Override
                public FrozenPigment accentColor(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
                    return null;
                }

                @Override
                public void execute(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context, @NotNull List<Iota> list) {

                }

                @Nullable
                @Override
                protected Component errorMessage(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
                    return Component.translatable("Not a golem!");
                }
            });
        }

        return List.of();
    }

    @NotNull
    @Override
    public CostMediaActionResult executeWithOpCount(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) {
        return DefaultImpls.executeWithOpCount(this,list,castingEnvironment);
    }

    @NotNull
    @Override
    public OperationResult operate(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage, @NotNull SpellContinuation spellContinuation) {
        return DefaultImpls.operate(this,castingEnvironment,castingImage,spellContinuation);
    }

    public abstract int getArgc();

    abstract List<Iota> execInEnvironment(@NotNull List<? extends Iota> list, @NotNull GolemCastingEnviorment castingEnvironment);
}
