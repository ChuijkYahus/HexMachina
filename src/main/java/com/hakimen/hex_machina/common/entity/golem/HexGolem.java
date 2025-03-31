package com.hakimen.hex_machina.common.entity.golem;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.common.lib.HexAttributes;
import com.hakimen.hex_machina.common.entity.golem.goals.HexGolemExecuteGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.ArrayList;
import java.util.List;

public class HexGolem extends PathfinderMob{

    public static final String HEX_IN_GOLEM = "Hex";


    List<Iota> golemHex;

    public HexGolem(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        golemHex = new ArrayList<>();
    }

    @Override
    public float getWalkTargetValue(BlockPos blockPos) {
        return 20;
    }

    @Override
    public float getWalkTargetValue(BlockPos blockPos, LevelReader levelReader) {
        return 20f;
    }

    @Override
    public AttributeMap getAttributes() {
        return new AttributeMap(createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0)
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ARMOR_TOUGHNESS, 0)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(HexAttributes.MEDIA_CONSUMPTION_MODIFIER, 0)
                .build());
    }

    @Override
    public boolean isInvulnerable() {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return false;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return List.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new HexGolemExecuteGoal(this));
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.put(HEX_IN_GOLEM, getHexAsListTag());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        readFromListTag(compoundTag.getList(HEX_IN_GOLEM,Tag.TAG_COMPOUND));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public List<Iota> getGolemHex() {
        return golemHex;
    }

    public HexGolem setGolemHex(List<Iota> golemHex) {
        this.golemHex = golemHex;
        return this;
    }

    public ListTag getHexAsListTag(){
        ListTag tag = new ListTag();

        for (Iota hex : golemHex) {
            tag.add(IotaType.serialize(hex));
        }

        return tag;
    }

    public void readFromListTag(ListTag tag){
        golemHex = new ArrayList<>();
        for (Tag iota : tag) {
            golemHex.add(IotaType.deserialize((CompoundTag) iota, (ServerLevel) level()));
        }
    }

    public void hexFromNBT(CompoundTag nbt){
        readFromListTag(nbt.getList(HEX_IN_GOLEM, Tag.TAG_COMPOUND));
    }

    public ListTag hexToNBT(){
        return getHexAsListTag();
    }

    public boolean writeIota(Iota iota){
        if(iota instanceof ListIota list){
            golemHex.clear();
            for (int i = 0; i < list.getList().size(); i++) {
                golemHex.add(list.getList().getAt(i));
            }
            return true;
        }
        return false;
    }
}
