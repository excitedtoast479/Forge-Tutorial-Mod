package com.excitedtoast479.tutorialmod.screen;

import com.excitedtoast479.tutorialmod.TutorialMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MediumVaseScreen extends AbstractContainerScreen<MediumVaseMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(TutorialMod.MOD_ID, "textures/gui/medium_vase_gui.png");


    public MediumVaseScreen(MediumVaseMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.titleLabelX = 50;
        this.titleLabelY = 4-33;
        this.inventoryLabelX = 6;
        this.inventoryLabelY = 130-33;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

            this.blit(pPoseStack, x, y - 35, 0, 0, imageWidth, imageHeight + 100);

    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
