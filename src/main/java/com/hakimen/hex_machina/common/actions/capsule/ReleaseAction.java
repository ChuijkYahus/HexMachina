package com.hakimen.hex_machina.common.actions.capsule;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadOffhandItem;
import at.petrak.hexcasting.api.misc.MediaConstants;
import com.hakimen.hex_machina.common.items.HexEntityHolder;
import com.hakimen.hex_machina.common.registry.ItemRegister;
import com.hakimen.hex_machina.common.utils.exceptions.ExceptionUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReleaseAction implements ConstMediaAction {
    public static final ReleaseAction INSTANCE = new ReleaseAction();


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

        Vec3 whereToRelease = OperatorUtils.getVec3(list, 0, getArgc());

        CastingEnvironment.HeldItemInfo info = castingEnvironment.getHeldItemToOperateOn(stack -> stack.getItem() instanceof HexEntityHolder && HexEntityHolder.hasEntity(stack));

        if (info != null) {
            ItemStack stack = info.stack();
            LivingEntity entity = HexEntityHolder.readEntityFromNBT(castingEnvironment.getWorld(),stack.getOrCreateTag());
            if(entity != null){
                entity.setPos(whereToRelease);
                castingEnvironment.getWorld().addFreshEntity(entity);
            }
        } else {
            ExceptionUtils.throwException(new MishapBadOffhandItem(ItemStack.EMPTY, ItemRegister.ENTITY_CAPSULE.get().getDefaultInstance().getHoverName().copy().append(" with a Entity")));
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
