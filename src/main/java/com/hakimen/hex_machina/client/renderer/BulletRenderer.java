package com.hakimen.hex_machina.client.renderer;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import at.petrak.hexcasting.common.particles.ConjureParticleOptions;
import com.hakimen.hex_machina.common.entity.BulletProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class BulletRenderer extends EntityRenderer<BulletProjectile> {
    public BulletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BulletProjectile entity) {
        return new ResourceLocation("minecraft", "empty");
    }

    @Override
    public void render(BulletProjectile entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {

        FrozenPigment pigment = FrozenPigment.DEFAULT.get();

        Player player = Minecraft.getInstance().player;
        Vec3 pos = entity.getPosition(g);

        if(player != null){
            pigment = HexAPI.instance().getColorizer(player);

            for (int j = 0; j < 16; j++) {
                Minecraft.getInstance().level.addAlwaysVisibleParticle(
                        new ConjureParticleOptions(pigment.getColorProvider().getColor((Minecraft.getInstance().level.getGameTime() * 5 + g), pos)), true,
                        pos.x + player.getRandom().triangle(-0.4,0.4) + 0.5, pos.y + player.getRandom().triangle(-0.4,0.4) + 0.25, pos.z + player.getRandom().triangle(-0.4,0.4) + 0.5,
                        0,0,0
                );
            }
        }
        super.render(entity, f, g, poseStack, multiBufferSource, i);

    }

    @Override
    public boolean shouldRender(BulletProjectile entity, Frustum frustum, double d, double e, double f) {
        return true;
    }

}
