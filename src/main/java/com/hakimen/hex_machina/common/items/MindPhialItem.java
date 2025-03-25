package com.hakimen.hex_machina.common.items;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.common.items.magic.ItemMediaHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class MindPhialItem extends ItemMediaHolder {
    public MindPhialItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public boolean canProvideMedia(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canRecharge(ItemStack stack) {
        return false;
    }

    @Override
    public long getMaxMedia(ItemStack stack) {
        return Long.MAX_VALUE;
    }

    public boolean isFull(ItemStack stack) {
        return getMedia(stack) == getMaxMedia(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        var maxMedia = getMaxMedia(pStack);
        if (maxMedia > 0) {
            DecimalFormat DUST_AMOUNT = new DecimalFormat("###,###.##");
            var media = getMedia(pStack);
            var mediamount = Component.literal(DUST_AMOUNT.format(media / (float) MediaConstants.DUST_UNIT));
            mediamount.withStyle(style -> style.withColor(HEX_COLOR));

            pTooltipComponents.add(
                    Component.translatable("hex_machina.tooltip.mind_media.amount",
                            mediamount).withStyle(Style.EMPTY.withColor(HEX_COLOR)));
        }
    }

    public void addMindMedia(float health, ItemStack stack){
        long oldMedia = getMedia(stack);
        double math = (Math.log10((oldMedia == 0 ? health : oldMedia) + health * MediaConstants.DUST_UNIT)/(health*MediaConstants.DUST_UNIT));
        this.setMedia(stack, oldMedia + (long)((1.25 - math) * health * MediaConstants.DUST_UNIT));
    }
}
