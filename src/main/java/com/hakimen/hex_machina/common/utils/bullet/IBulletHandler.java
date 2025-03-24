package com.hakimen.hex_machina.common.utils.bullet;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IBulletHandler {
    InteractionResultHolder<ItemStack> handleBullet(ItemStack gun, ItemStack bullet, Player player, Level level, InteractionHand hand);
}
