package com.hakimen.hex_machina.common.actions.capsule;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.RenderedSpell;
import at.petrak.hexcasting.api.casting.castables.SpellAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadEntity;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadOffhandItem;
import at.petrak.hexcasting.api.misc.MediaConstants;
import com.hakimen.hex_machina.common.items.HexEntityHolder;
import com.hakimen.hex_machina.common.registry.ItemRegister;
import com.hakimen.hex_machina.common.utils.exceptions.ExceptionUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ContainAction implements SpellAction {
    public static final ContainAction INSTANCE = new ContainAction();


    @Override
    public int getArgc() {
        return 1;
    }


    @Override
    public @NotNull Result execute(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) {

        LivingEntity toContain = OperatorUtils.getLivingEntityButNotArmorStand(list, 0, getArgc());


        if(!HexEntityHolder.canCapture(toContain)){
            ExceptionUtils.throwException(new MishapBadEntity(toContain, Component.literal("Any entity excluding Player, Wither and Ender Dragon")));
        }

        CastingEnvironment.HeldItemInfo info = castingEnvironment.getHeldItemToOperateOn(stack -> stack.getItem() instanceof HexEntityHolder);


        float health = toContain.getMaxHealth();

        if (info != null) {
            ItemStack stack = info.stack();
            if(!HexEntityHolder.hasEntity(stack)){
                HexEntityHolder.writeEntityToNBT(stack.getOrCreateTag(), toContain);
                toContain.remove(Entity.RemovalReason.DISCARDED);
            }else{
                ExceptionUtils.throwException(new MishapBadOffhandItem(ItemStack.EMPTY, Component.translatable("mishap.hex_machina.entity_capsule.expect_empty", ItemRegister.ENTITY_CAPSULE.get().getDefaultInstance().getHoverName())));
            }
        } else {
            ExceptionUtils.throwException(new MishapBadOffhandItem(ItemStack.EMPTY, ItemRegister.ENTITY_CAPSULE.get().getDefaultInstance().getHoverName()));
        }

        return new Result(new Spell(), (int)Math.floor(MediaConstants.DUST_UNIT * health), List.of(
                new ParticleSpray(castingEnvironment.getCastingEntity().position(), new Vec3(0.0, 1.5, 0.0), 0.4, Math.PI / 3, 30)
        ), 1);
    }

    @NotNull
    @Override
    public OperationResult operate(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage, @NotNull SpellContinuation spellContinuation) {
        return DefaultImpls.operate(this, castingEnvironment, castingImage, spellContinuation);
    }

    @NotNull
    @Override
    public Result executeWithUserdata(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment, @NotNull CompoundTag compoundTag) {
        return DefaultImpls.executeWithUserdata(this, list, castingEnvironment, compoundTag);
    }

    @Override
    public boolean hasCastingSound(@NotNull CastingEnvironment castingEnvironment) {
        return false;
    }

    @Override
    public boolean awardsCastingStat(@NotNull CastingEnvironment castingEnvironment) {
        return false;
    }


    static class Spell implements RenderedSpell{


        @Override
        public void cast(@NotNull CastingEnvironment castingEnvironment) {

        }

        @Nullable
        @Override
        public CastingImage cast(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage) {
            return DefaultImpls.cast(this, castingEnvironment, castingImage);
        }
    }
}
