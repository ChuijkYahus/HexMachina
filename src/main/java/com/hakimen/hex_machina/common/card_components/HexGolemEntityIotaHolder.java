package com.hakimen.hex_machina.common.card_components;

import at.petrak.hexcasting.api.addldata.ADIotaHolder;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.utils.NBTHelper;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import at.petrak.hexcasting.fabric.cc.adimpl.CCIotaHolder;
import com.hakimen.hex_machina.common.entity.golem.HexGolem;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public class HexGolemEntityIotaHolder implements CCIotaHolder {

    HexGolem golem;

    public HexGolemEntityIotaHolder(HexGolem golem) {
        this.golem = golem;
    }

    @Override
    public @Nullable CompoundTag readIotaTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("Hex", golem.hexToNBT());
        return tag;
    }

    @Override
    public boolean writeIota(@Nullable Iota iota, boolean simulate) {
        return iota != null && iota.getType().equals(HexIotaTypes.LIST) && golem.writeIota(iota) && writeable();
    }

    @Override
    public boolean writeable() {
        return true;
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag) {
        golem.hexFromNBT(compoundTag);
    }

    @Override
    public void writeToNbt(CompoundTag compoundTag) {
        compoundTag.put("Hex", golem.hexToNBT());
    }
}
