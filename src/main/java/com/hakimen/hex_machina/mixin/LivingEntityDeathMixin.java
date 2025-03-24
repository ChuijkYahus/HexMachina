package com.hakimen.hex_machina.mixin;

import com.hakimen.hex_machina.common.items.MindPhialItem;
import com.hakimen.hex_machina.common.registry.EffectRegister;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Optional;

@Mixin(LivingEntity.class)
public class LivingEntityDeathMixin {

    @Inject(at=@At("HEAD"), method = "die")
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        Entity causer = damageSource.getEntity();
        if (causer instanceof Player player && livingEntity.hasEffect(EffectRegister.EXPOSE_MIND.get())) {
            Inventory inventory = player.getInventory();

            ArrayList<ItemStack> ALL = new ArrayList<>();

            ALL.addAll(inventory.offhand);
            ALL.addAll(inventory.items);
            ALL.addAll(inventory.armor);

            Optional < ItemStack > possiblyMindPhial = ALL.stream().filter(stack -> stack.getItem() instanceof MindPhialItem mindPhialItem && !mindPhialItem.isFull(stack)).findFirst();
            possiblyMindPhial.ifPresent(itemStack -> {
                if(itemStack.getItem() instanceof MindPhialItem item) {
                    item.addMindMedia(livingEntity.getMaxHealth(), itemStack);
                }
            });
        }
    }
}
