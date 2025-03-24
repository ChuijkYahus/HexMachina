package com.hakimen.hex_machina.common.actions.gun;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.misc.MediaConstants;
import com.hakimen.hex_machina.common.container.NonEmptyContainer;
import com.hakimen.hex_machina.common.hex.mishaps.MishapNotGun;
import com.hakimen.hex_machina.common.items.HexGunItem;
import com.hakimen.hex_machina.common.utils.exceptions.ExceptionUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.hakimen.hex_machina.common.items.HexGunItem.GUN_DATA;
import static com.hakimen.hex_machina.common.items.HexGunItem.MAX_COUNT;

public class CycleBulletWithArgsAction implements ConstMediaAction {

    public static final CycleBulletWithArgsAction INSTANCE = new CycleBulletWithArgsAction();

    @Override
    public int getArgc() {
        return 1;
    }

    @Override
    public long getMediaCost() {
        return 0;
    }

    @NotNull
    @Override
    public List<Iota> execute(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) {
        @Nullable LivingEntity caster = castingEnvironment.getCastingEntity();

        int step = OperatorUtils.getInt(list, 0, getArgc());

        if(caster instanceof Player player){
            ItemStack stack = player.getItemInHand(castingEnvironment.getCastingHand());
            if(stack.getItem() instanceof HexGunItem){
                NonEmptyContainer container = new NonEmptyContainer(MAX_COUNT);
                container.fromTag(stack.getOrCreateTag().getList(GUN_DATA, Tag.TAG_COMPOUND));


                int currentBullet = stack.getOrCreateTag().getInt(HexGunItem.BULLET_INDEX_COUNTER);
                List<Integer> slots = new ArrayList<>();

                for (int i = 0; i < container.items.size(); i++) {
                    if(!container.items.get(i).isEmpty()){
                        slots.add(i);
                    }
                }
                int count = currentBullet + step;

                stack.getOrCreateTag().putInt(HexGunItem.BULLET_INDEX_COUNTER, count < 0 ? slots.size() + step : count % slots.size());
                stack.getOrCreateTag().putInt(HexGunItem.BULLET_SLOT, slots.get( count < 0 ? slots.size() + step : count % slots.size()));

            }else{
                ExceptionUtils.throwException(new MishapNotGun());
            }
        }
        return List.of();
    }

    @NotNull
    @Override
    public ConstMediaAction.CostMediaActionResult executeWithOpCount(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) {
        return DefaultImpls.executeWithOpCount(this, list, castingEnvironment);
    }

    @NotNull
    @Override
    public OperationResult operate(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage, @NotNull SpellContinuation spellContinuation) {
        return DefaultImpls.operate(this,castingEnvironment,castingImage,spellContinuation);
    }
}
