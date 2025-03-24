package com.hakimen.hex_machina.client.menus;

import com.hakimen.hex_machina.common.container.NonEmptyContainer;
import com.hakimen.hex_machina.common.items.bullets.AmethystBulletItem;
import com.hakimen.hex_machina.common.registry.ContainerRegister;
import com.hakimen.hex_machina.common.registry.ItemRegister;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import static com.hakimen.hex_machina.common.items.HexGunItem.*;

public class GunMenu extends AbstractContainerMenu {

    ItemStack gun;
    Container playerInventory;
    SimpleContainer gunContainer;

    int currentBullet;

    public GunMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        super(ContainerRegister.GUN_MENU.get(), pContainerId);

        playerInventory = pInventory;
        gun = pPlayer.getMainHandItem().getItem().equals(ItemRegister.HEX_GUN.get()) ? pPlayer.getMainHandItem() : (pPlayer.getOffhandItem().getItem().equals(ItemRegister.HEX_GUN.get()) ? pPlayer.getOffhandItem() : ItemStack.EMPTY);


        ListTag tag = gun.getOrCreateTag().getList(GUN_DATA, Tag.TAG_COMPOUND);
        gunContainer = new NonEmptyContainer(6);

        if (!tag.isEmpty()) {
            gunContainer.fromTag(tag);
        }
        addSlot(new GunSlot(gunContainer, 0, 80, 19));
        addSlot(new GunSlot(gunContainer, 1, 113, 39));
        addSlot(new GunSlot(gunContainer, 2, 113, 77));
        addSlot(new GunSlot(gunContainer, 3, 80, 97));
        addSlot(new GunSlot(gunContainer, 4, 47, 77));
        addSlot(new GunSlot(gunContainer, 5, 47, 39));

        layoutPlayerInventorySlots(8, 140);

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return currentBullet;
            }

            @Override
            public void set(int i) {
                currentBullet = i;
            }
        });

        setData(0, gun.getOrCreateTag().getInt(BULLET_SLOT));
    }

    public ItemStack getGun() {
        return gun;
    }

    public GunMenu setGun(ItemStack gun) {
        this.gun = gun;
        return this;
    }

    public int getCurrentBullet() {
        return currentBullet;
    }

    private int addSlotRange(Container handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(Container handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        int size = 6;
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();
            if (i < size && !this.moveItemStackTo(stack1, size, this.slots.size(), true)) {
                slot.setChanged();
                return ItemStack.EMPTY;
            }
            if (!this.moveItemStackTo(stack1, 0, size, false)) {
                slot.setChanged();
                return ItemStack.EMPTY;
            }
            if (stack1.isEmpty()) {
                slot.mayPlace(ItemStack.EMPTY);
                slot.setChanged();
            }
            if (stack1.getCount() == stack.getCount()) {
                slot.setChanged();
                return ItemStack.EMPTY;
            } else {
                slot.setChanged();
            }
        }
        gunContainer.setChanged();
        return stack;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }

    class GunSlot extends Slot {

        public GunSlot(Container container, int i, int j, int k) {
            super(container, i, j, k);
        }

        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return itemStack.getItem() instanceof AmethystBulletItem;
        }

        @Override
        public void setChanged() {
            if(container instanceof NonEmptyContainer container){
                gun.getOrCreateTag().put(GUN_DATA, container.createTag());
                gun.getOrCreateTag().putInt(BULLET_INDEX_COUNTER, 0);
                for (int i = 0; i < container.items.size(); i++) {
                    if(!container.getItem(i).isEmpty()){
                        gun.getOrCreateTag().putInt(BULLET_SLOT, i );
                        break;
                    }
                }


                currentBullet = gun.getOrCreateTag().getInt(BULLET_SLOT);
            }
            super.setChanged();
        }
    }

}
