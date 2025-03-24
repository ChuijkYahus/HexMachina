package com.hakimen.hex_machina.common.handlers;

import com.hakimen.hex_machina.common.entity.BulletProjectile;
import com.hakimen.hex_machina.common.handlers.bullet.DefaultCastBullet;
import com.hakimen.hex_machina.common.items.bullets.AmethystBulletItem;
import com.hakimen.hex_machina.common.registry.ItemRegister;
import com.hakimen.hex_machina.common.utils.bullet.IBulletHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.function.Supplier;

public class BulletHandler {
    public static final HashMap<Item, Supplier<IBulletHandler>> HANDLERS = new HashMap<>();

    static {
        HANDLERS.put(ItemRegister.AMETHYST_BULLET.get(), DefaultCastBullet::new);
        HANDLERS.put(ItemRegister.BLOCK_PROJECTILE_BULLET.get(), () -> (IBulletHandler) (gun, bullet, player, level, hand) -> {
            if(level instanceof ServerLevel serverLevel && bullet.getItem() instanceof AmethystBulletItem amethystBulletItem){
                BulletProjectile bulletProjectile = new BulletProjectile(level, amethystBulletItem.getAsListOfIotas(bullet, serverLevel), true, gun, player.getUUID());
                bulletProjectile.setPos(player.getEyePosition());
                bulletProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3F, 1.0F);
                bulletProjectile.setOwner(player);
                serverLevel.addFreshEntity(bulletProjectile);
            }
            return null;
        });
        HANDLERS.put(ItemRegister.TARGET_PROJECTILE_BULLET.get(),  () -> (IBulletHandler) (gun, bullet, player, level, hand) -> {
            if(level instanceof ServerLevel serverLevel && bullet.getItem() instanceof AmethystBulletItem amethystBulletItem){
                BulletProjectile bulletProjectile = new BulletProjectile(level, amethystBulletItem.getAsListOfIotas(bullet, serverLevel), false, gun, player.getUUID());
                bulletProjectile.setPos(player.getEyePosition());
                bulletProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3F, 1.0F);
                bulletProjectile.setOwner(player);

                serverLevel.addFreshEntity(bulletProjectile);
            }
            return null;
        });
    }
}
