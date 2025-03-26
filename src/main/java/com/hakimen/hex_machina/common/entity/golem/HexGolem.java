package com.hakimen.hex_machina.common.entity.golem;

import at.petrak.hexcasting.common.lib.HexAttributes;
import com.hakimen.hex_machina.common.entity.golem.goals.HexGolemExecuteGoal;
import net.minecraft.core.BlockPos;
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

import java.util.List;

public class HexGolem extends PathfinderMob {

    public HexGolem(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
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
                .add(Attributes.MAX_HEALTH, 100)
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
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource.is(DamageTypes.FELL_OUT_OF_WORLD);
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
    public void tick() {
        super.tick();
    }


    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}
