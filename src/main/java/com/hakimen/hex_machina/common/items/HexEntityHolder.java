package com.hakimen.hex_machina.common.items;

import at.petrak.hexcasting.fabric.cc.HexCardinalComponents;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.internal.entity.CardinalComponentsEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class HexEntityHolder extends Item {

    public static final String TAG_ENTITY = "Entity";
    public static final String TAG_ENTITY_TYPE = "Type";
    public static final String TAG_CC_DATA = "CC";

    public HexEntityHolder() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        if (hasEntity(itemStack)) {
           list.add( BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(itemStack.getOrCreateTag().getString(TAG_ENTITY_TYPE)))
                   .getDescription().copy().withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

    public static void writeEntityToNBT(CompoundTag tag, LivingEntity entity) {
        if (canCapture(entity)) {
            CompoundTag entityTag = new CompoundTag();
            entity.save(entityTag);
            tag.put(TAG_ENTITY, entityTag);
            tag.putString(TAG_ENTITY_TYPE, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
        }
    }

    public static boolean canCapture(LivingEntity entity){
        return !(entity instanceof Player) && !(entity instanceof WitherBoss) && !(entity instanceof EnderDragon);
    }

    public static LivingEntity readEntityFromNBT(Level level, CompoundTag tag) {
        Entity entity = BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(tag.getString(TAG_ENTITY_TYPE))).create(level);
        if(entity instanceof LivingEntity livingEntity){
            livingEntity.load(tag.getCompound(TAG_ENTITY));

            tag.remove(TAG_ENTITY);
            tag.remove(TAG_ENTITY_TYPE);
            return livingEntity;
        }

        return null;
    }

    public static boolean hasEntity(ItemStack stack){
        return stack.hasTag() && stack.getOrCreateTag().contains(TAG_ENTITY);
    }

}
