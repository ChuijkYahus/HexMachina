package com.hakimen.hex_machina.common.items.bullets;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.hexcasting.api.utils.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AmethystBulletItem extends Item implements IotaHolderItem {
    public AmethystBulletItem() {
        super(new Item.Properties().stacksTo(1));
    }

    public static final String TAG_DATA = "data";

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        IotaHolderItem.appendHoverText(this, itemStack, list, tooltipFlag);
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

    @Override
    public @Nullable CompoundTag readIotaTag(ItemStack stack) {
        return NBTHelper.getCompound(stack, TAG_DATA);
    }

    @Override
    public boolean writeable(ItemStack stack) {
        return true;
    }

    public boolean hasData(ItemStack stack){
        return NBTHelper.hasCompound(stack, TAG_DATA);
    }

    public List<Iota> getAsListOfIotas(ItemStack stack, ServerLevel level){
        var patsTag = NBTHelper.getCompound(stack, TAG_DATA);


        if (patsTag == null) {
            return null;
        }

        var patternsList = patsTag.getList("hexcasting:data", Tag.TAG_COMPOUND);
        var out = new ArrayList<Iota>();
        for (var patTag : patternsList) {
            CompoundTag tag = NBTHelper.getAsCompound(patTag);
            out.add(IotaType.deserialize(tag, level));
        }

        return out;
    }

    @Override
    public boolean canWrite(ItemStack stack, @Nullable Iota iota) {
        return iota != null && writeable(stack);
    }

    @Override
    public void writeDatum(ItemStack stack, @Nullable Iota iota) {
        NBTHelper.putCompound(stack, TAG_DATA, IotaType.serialize(iota));
    }
}
