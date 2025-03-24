package com.hakimen.hex_machina.common.registry;

import com.hakimen.hex_machina.HexMachina;
import com.hakimen.hex_machina.common.items.HexEntityHolder;
import com.hakimen.hex_machina.common.items.MindPhialItem;
import com.hakimen.hex_machina.common.items.ScryingGogglesItem;
import com.hakimen.hex_machina.common.items.bullets.AmethystBulletItem;
import com.hakimen.hex_machina.common.items.HexGunItem;
import com.hakimen.hex_machina.common.utils.registration.IRegistry;
import com.hakimen.hex_machina.common.utils.registration.Recorder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ItemRegister implements IRegistry {

    public static final ItemRegister ITEM_REGISTER = new ItemRegister();
    public static final Recorder<Item> ITEMS = new Recorder<>(BuiltInRegistries.ITEM, HexMachina.MODID);
    public static final Recorder<CreativeModeTab> TABS = new Recorder<>(BuiltInRegistries.CREATIVE_MODE_TAB, HexMachina.MODID);

    public static final Supplier<HexGunItem> HEX_GUN = ITEMS.register("hex_gun", HexGunItem::new);
    public static final Supplier<AmethystBulletItem> AMETHYST_BULLET = ITEMS.register("amethyst_bullet", AmethystBulletItem::new);
    public static final Supplier<AmethystBulletItem> BLOCK_PROJECTILE_BULLET = ITEMS.register("amethyst_bullet/block_projectile", AmethystBulletItem::new);
    public static final Supplier<AmethystBulletItem> TARGET_PROJECTILE_BULLET = ITEMS.register("amethyst_bullet/target_projectile", AmethystBulletItem::new);

    public static final Supplier<ScryingGogglesItem> SCRYING_GOOGLES = ITEMS.register("scrying_goggles", ScryingGogglesItem::new);
    public static final Supplier<HexEntityHolder> ENTITY_CAPSULE = ITEMS.register("entity_capsule", HexEntityHolder::new);
    public static final Supplier<MindPhialItem> MIND_PHIAL = ITEMS.register("mind_phial", MindPhialItem::new);

    public static final Supplier<CreativeModeTab> HEX_MACHINA_TAB = TABS.register("hex_machina", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .title(Component.translatable("tab.hex_machina"))
            .icon(() -> new ItemStack(HEX_GUN.get()))
            .displayItems((itemDisplayParameters, output) -> {
                ITEMS.getHolder().forEach(output::accept);
            }).build());

    @Override
    public void register() {

    }
}
