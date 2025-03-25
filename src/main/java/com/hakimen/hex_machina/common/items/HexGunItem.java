package com.hakimen.hex_machina.common.items;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import com.hakimen.hex_machina.common.container.NonEmptyContainer;
import com.hakimen.hex_machina.common.handlers.BulletHandler;
import com.hakimen.hex_machina.common.items.bullets.AmethystBulletItem;
import com.hakimen.hex_machina.common.registry.ContainerRegister;
import com.hakimen.hex_machina.common.utils.bullet.IBulletHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HexGunItem extends Item implements IotaHolderItem {

    public static final String GUN_DATA = "GunData";
    public static final String BULLET_INDEX_COUNTER = "Index";
    public static final String BULLET_SLOT = "CurrentBulletSlot";

    public static final Integer MAX_COUNT = 6;

    public static final Integer RELOAD_COOLDOWN = 5;
    public static final Integer SHOT_DELAY = 5;

    public HexGunItem() {
        super(new Item.Properties().stacksTo(1));
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        int slot = itemStack.getOrCreateTag().getInt(BULLET_SLOT);
        ListTag tag = itemStack.getOrCreateTag().getList(GUN_DATA, Tag.TAG_COMPOUND);

        if(!tag.isEmpty()){
            CompoundTag slotData = tag.getCompound(slot);
            ItemStack gunStack = ItemStack.of(slotData);
            if(gunStack.getItem() instanceof AmethystBulletItem bullet){
                list.add(Component.translatable("%s", gunStack.getHoverName()).withStyle(ChatFormatting.LIGHT_PURPLE));
                bullet.appendHoverText(gunStack, level, list, new TooltipFlag.Default(false,false));
            }
        }

        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if(!stack.getOrCreateTag().contains(BULLET_INDEX_COUNTER)){
            stack.getOrCreateTag().putInt(BULLET_INDEX_COUNTER, 0);
        }
        if(!stack.getOrCreateTag().contains(BULLET_SLOT)){
            stack.getOrCreateTag().putInt(BULLET_SLOT, 0);
        }

        if(player.isShiftKeyDown()) {
            MenuProvider containerProvider = new MenuProvider() {

                @Override
                public Component getDisplayName() {
                    return Component.empty();
                }

                @Override
                public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                    return ContainerRegister.GUN_MENU.get().create(windowId, playerInventory);
                }
            };
            player.openMenu(containerProvider);
        }else{
            NonEmptyContainer container = new NonEmptyContainer(MAX_COUNT);
            container.fromTag(stack.getOrCreateTag().getList(GUN_DATA, Tag.TAG_COMPOUND));

            List<Integer> slots = new ArrayList<>();

            for (int i = 0; i < container.items.size(); i++) {
                if(!container.items.get(i).isEmpty()){
                    slots.add(i);
                }
            }

            if(container.isEmpty()) return InteractionResultHolder.fail(stack);


            var before = stack.getOrCreateTag().getInt(BULLET_INDEX_COUNTER);
            ItemStack containerStack = container.getItem(slots.get(stack.getOrCreateTag().getInt(BULLET_INDEX_COUNTER)));
            Item item = containerStack.getItem();

            stack.getOrCreateTag().put(GUN_DATA, container.createTag());
            stack.getOrCreateTag().putInt(BULLET_INDEX_COUNTER, (stack.getOrCreateTag().getInt(BULLET_INDEX_COUNTER) + 1) % slots.size());
            stack.getOrCreateTag().putInt(BULLET_SLOT, slots.get(stack.getOrCreateTag().getInt(BULLET_INDEX_COUNTER)));


            if(item instanceof AmethystBulletItem itemHex){
                IBulletHandler handler = BulletHandler.HANDLERS.getOrDefault(itemHex, () -> null).get();

                if(handler != null){
                    var result = handler.handleBullet(stack, containerStack, player, level, interactionHand);;
                    if(result != null){
                        return result;
                    }
                }
                player.getCooldowns().addCooldown(this, before == stack.getOrCreateTag().getInt(BULLET_INDEX_COUNTER) ? SHOT_DELAY : (stack.getOrCreateTag().getInt(BULLET_INDEX_COUNTER) == 0 ? (RELOAD_COOLDOWN * slots.size()) : SHOT_DELAY));
                return InteractionResultHolder.fail(stack);
            }
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.CROSSBOW;
    }

    @Override
    public @Nullable CompoundTag readIotaTag(ItemStack stack) {
        NonEmptyContainer container = new NonEmptyContainer(MAX_COUNT);
        int slot = stack.getOrCreateTag().getInt(BULLET_SLOT);
        container.fromTag(stack.getOrCreateTag().getList(GUN_DATA, Tag.TAG_COMPOUND));

        ItemStack bullet = container.getItem(slot);

        if(!bullet.isEmpty() && bullet.getItem() instanceof AmethystBulletItem bulletItem){
            return bulletItem.readIotaTag(bullet);
        }

        return null;
    }

    @Override
    public boolean writeable(ItemStack stack) {
        NonEmptyContainer container = new NonEmptyContainer(MAX_COUNT);
        int slot = stack.getOrCreateTag().getInt(BULLET_SLOT);
        container.fromTag(stack.getOrCreateTag().getList(GUN_DATA, Tag.TAG_COMPOUND));

        ItemStack bullet = container.getItem(slot);

        return (!bullet.isEmpty() && bullet.getItem() instanceof AmethystBulletItem amethystBulletItem && amethystBulletItem.writeable(bullet));
    }

    @Override
    public boolean canWrite(ItemStack stack, @Nullable Iota iota) {
        NonEmptyContainer container = new NonEmptyContainer(MAX_COUNT);
        int slot = stack.getOrCreateTag().getInt(BULLET_SLOT);
        container.fromTag(stack.getOrCreateTag().getList(GUN_DATA, Tag.TAG_COMPOUND));

        ItemStack bullet = container.getItem(slot);

        return  (!bullet.isEmpty() && bullet.getItem() instanceof AmethystBulletItem amethystBulletItem && amethystBulletItem.canWrite(bullet, iota));
    }

    @Override
    public void writeDatum(ItemStack stack, @Nullable Iota iota) {
        NonEmptyContainer container = new NonEmptyContainer(MAX_COUNT);
        int slot = stack.getOrCreateTag().getInt(BULLET_SLOT);
        container.fromTag(stack.getOrCreateTag().getList(GUN_DATA, Tag.TAG_COMPOUND));

        ItemStack bullet = container.getItem(slot);

        if(!bullet.isEmpty() && bullet.getItem() instanceof AmethystBulletItem amethystBulletItem){
            amethystBulletItem.writeDatum(bullet, iota);
            stack.getOrCreateTag().put(GUN_DATA, container.createTag());
        }
    }
}
