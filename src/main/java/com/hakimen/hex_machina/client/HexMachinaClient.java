package com.hakimen.hex_machina.client;

import com.hakimen.hex_machina.HexMachina;
import com.hakimen.hex_machina.client.renderer.BulletRenderer;
import com.hakimen.hex_machina.client.screen.GunScreen;
import com.hakimen.hex_machina.common.items.MindPhialItem;
import com.hakimen.hex_machina.common.registry.ContainerRegister;
import com.hakimen.hex_machina.common.registry.EntityRegister;
import com.hakimen.hex_machina.common.registry.ItemRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class HexMachinaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MenuScreens.register(ContainerRegister.GUN_MENU.get(), GunScreen::new);

        EntityRendererRegistry.register(EntityRegister.BULLET_PROJECTILE.get(), BulletRenderer::new);

        ItemProperties.register(ItemRegister.MIND_PHIAL.get(), new ResourceLocation(HexMachina.MODID,"phial"), (itemStack, clientLevel, livingEntity, i) -> {
            if(itemStack.getItem() instanceof MindPhialItem item){
                return item.getMedia(itemStack) != 0 ? 1 : 0;
            }

            return 0;
        });
    }
}
