package com.hakimen.hex_machina.client.renderer;

import com.hakimen.hex_machina.common.entity.golem.HexGolem;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;

import java.util.UUID;

public class HexGolemRenderer extends EntityRenderer<HexGolem> {
    public HexGolemRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(HexGolem entity) {
        return null;
    }

    AbstractClientPlayer player;

    @Override
    public void render(HexGolem entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {

        PlayerRenderer renderer = (PlayerRenderer) entityRenderDispatcher.getRenderer(Minecraft.getInstance().player);

        if(player == null){
            player = new AbstractClientPlayer(Minecraft.getInstance().level, new GameProfile(UUID.randomUUID(), "Golem")) {};
            player.calculateEntityAnimation(true);
        }


        renderer.render(player, f,g,poseStack, multiBufferSource,i);


        super.render(entity, f, g, poseStack, multiBufferSource, i);
    }
}
