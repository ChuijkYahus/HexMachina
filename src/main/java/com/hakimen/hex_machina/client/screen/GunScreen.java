package com.hakimen.hex_machina.client.screen;

import com.hakimen.hex_machina.HexMachina;
import com.hakimen.hex_machina.client.menus.GunMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class GunScreen extends AbstractContainerScreen<GunMenu> {
    private final ResourceLocation GUI = new ResourceLocation(HexMachina.MODID, "textures/gui/hex_gun.png");
    private final ResourceLocation SLOT_SELECTOR = new ResourceLocation(HexMachina.MODID, "textures/gui/selected_overlay.png");



    public GunScreen(GunMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        this.imageHeight = 120 + 6 * 18;
        this.inventoryLabelY = this.imageHeight - 98;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, i, j, f);
        super.renderTooltip(guiGraphics, i, j);

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight + 2);

        int slotIdx = menu.getCurrentBullet();

        Slot slot = menu.slots.get(slotIdx);

        guiGraphics.blit(SLOT_SELECTOR, relX + slot.x - 2, relY + slot.y -2, 0,0, 20,20,20,20);
    }
}
