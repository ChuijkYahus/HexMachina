package com.hakimen.hex_machina.common.handlers.bullet;

import com.hakimen.hex_machina.common.utils.bullet.BulletUtils;
import com.hakimen.hex_machina.common.utils.bullet.IBulletHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;


public class DefaultCastBullet implements IBulletHandler {
    @Override
    public InteractionResultHolder<ItemStack> handleBullet(ItemStack gun, ItemStack bullet, Player player, Level level, InteractionHand hand) {
        return BulletUtils.prefixCast(gun, bullet, player, level, hand, List.of());
    }
}
