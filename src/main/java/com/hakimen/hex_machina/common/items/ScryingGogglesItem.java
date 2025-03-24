package com.hakimen.hex_machina.common.items;

import at.petrak.hexcasting.common.lib.HexAttributes;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ScryingGogglesItem extends Item implements Equipable{

    public static final AttributeModifier GRID_ZOOM = new AttributeModifier(
            UUID.fromString("f3ebeff0-a2ce-4a29-b05a-34ddf01df6b6"),
            "Scrying Lens Zoom", 0.5, AttributeModifier.Operation.MULTIPLY_BASE);

    public static final AttributeModifier SCRY_SIGHT = new AttributeModifier(
            UUID.fromString("e94ff98b-c64a-4638-801f-ad4bec1c9cc1"),
            "Scrying Lens Sight", 1.0, AttributeModifier.Operation.ADDITION);

    public ScryingGogglesItem() {
        super(new Item.Properties().stacksTo(1).durability(-1));
        DispenserBlock.registerBehavior(this, new OptionalDispenseItemBehavior() {
            protected @NotNull
            ItemStack execute(@NotNull BlockSource world, @NotNull ItemStack stack) {
                this.setSuccess(ArmorItem.dispenseArmor(world, stack));
                return stack;
            }
        });
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        var out = HashMultimap.<Attribute, AttributeModifier>create();
        if (slot == EquipmentSlot.HEAD ) {
            out.put(HexAttributes.GRID_ZOOM, GRID_ZOOM);
            out.put(HexAttributes.SCRY_SIGHT, SCRY_SIGHT);
        }
        return out;
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
