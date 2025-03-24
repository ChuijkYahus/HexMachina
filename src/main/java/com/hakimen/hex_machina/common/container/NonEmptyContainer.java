package com.hakimen.hex_machina.common.container;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class NonEmptyContainer extends SimpleContainer {

    public NonEmptyContainer(int i) {
        super(i);
    }

    @Override
    public void fromTag(ListTag listTag) {
        this.clearContent();

        for(int i = 0; i < listTag.size(); ++i) {
            ItemStack itemStack = ItemStack.of(listTag.getCompound(i));
            this.setItem(i, itemStack);
        }
    }

    @Override
    public ListTag createTag() {
        ListTag listTag = new ListTag();

        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemStack = this.getItem(i);
            listTag.add(i, itemStack.save(new CompoundTag()));
        }

        return listTag;
    }
}
