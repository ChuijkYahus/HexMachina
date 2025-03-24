package com.hakimen.hex_machina.mixin;

import com.hakimen.hex_machina.common.registry.ItemRegister;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class GetArmPoseMixin {

    @Inject(at = @At("RETURN"), method = "getArmPose", cancellable = true)
    private static void getArmPose(AbstractClientPlayer abstractClientPlayer, InteractionHand interactionHand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        ItemStack itemStack = abstractClientPlayer.getItemInHand(interactionHand);

        if(itemStack.is(ItemRegister.HEX_GUN.get())){
            cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
