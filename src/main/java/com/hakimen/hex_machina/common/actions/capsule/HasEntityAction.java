package com.hakimen.hex_machina.common.actions.capsule;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadOffhandItem;
import com.hakimen.hex_machina.common.items.HexEntityHolder;
import com.hakimen.hex_machina.common.registry.ItemRegister;
import com.hakimen.hex_machina.common.utils.exceptions.ExceptionUtils;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HasEntityAction implements ConstMediaAction {
    public static final HasEntityAction INSTANCE = new HasEntityAction();


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

        CastingEnvironment.HeldItemInfo info = castingEnvironment.getHeldItemToOperateOn(stack -> stack.getItem() instanceof HexEntityHolder);

        if (info != null) {
            ItemStack stack = info.stack();
            return List.of(new BooleanIota(HexEntityHolder.hasEntity(stack)));
        } else {
            ExceptionUtils.throwException(new MishapBadOffhandItem(ItemStack.EMPTY, ItemRegister.ENTITY_CAPSULE.get().getDefaultInstance().getHoverName().copy()));
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
