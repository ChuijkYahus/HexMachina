package com.hakimen.hex_machina.common.registry;

import com.hakimen.hex_machina.HexMachina;
import com.hakimen.hex_machina.common.entity.BulletProjectile;
import com.hakimen.hex_machina.common.utils.registration.IRegistry;
import com.hakimen.hex_machina.common.utils.registration.Recorder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;

import java.util.function.Supplier;

public class EntityRegister implements IRegistry {
    public static final EntityRegister ENTITY_REGISTER = new EntityRegister();

    public static final Recorder<EntityType<?>> ENTITY_TYPES = new Recorder<>(BuiltInRegistries.ENTITY_TYPE, HexMachina.MODID);

    public static final Supplier<EntityType<BulletProjectile>> BULLET_PROJECTILE = ENTITY_TYPES.register("bullet_projectile", () -> (EntityType<BulletProjectile>)(Object)EntityType.Builder.of(
            (entityType, level) -> new BulletProjectile(level), MobCategory.MISC
    ).sized(0.25f,0.25f).build("bullet_projectile"));

    @Override
    public void register() {

    }
}
