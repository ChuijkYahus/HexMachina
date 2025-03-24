package com.hakimen.hex_machina.common.registry;

import com.hakimen.hex_machina.HexMachina;
import com.hakimen.hex_machina.client.menus.GunMenu;
import com.hakimen.hex_machina.common.utils.registration.IRegistry;
import com.hakimen.hex_machina.common.utils.registration.Recorder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ContainerRegister implements IRegistry {

    public static <T extends AbstractContainerMenu>  MenuType<T> makeMenu(MenuType.MenuSupplier<T> data){
        return new MenuType<>(data, FeatureFlags.DEFAULT_FLAGS);
    }

    public static final ContainerRegister CONTAINER_REGISTER = new ContainerRegister();
    public static final Recorder<MenuType<?>> MENUS = new Recorder<>(BuiltInRegistries.MENU, HexMachina.MODID);

    public static final Supplier<MenuType<GunMenu>> GUN_MENU = MENUS.register("gun_menu", () ->  makeMenu((i, inventory) -> new GunMenu(i, inventory, inventory.player)));

    @Override
    public void register() {

    }
}
