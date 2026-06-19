package com.formlesslab.ae2additions.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.awt.*;

public final class FluidStackRenderer {


    public static void drawFluid(Minecraft minecraft, Rectangle rectangle, int capacityMb, @Nullable FluidStack fluidStack) {
        drawFluid(minecraft, rectangle.x, rectangle.y, rectangle.width, rectangle.height, capacityMb, fluidStack);
    }

    public static void drawFluid(Minecraft minecraft, int xPosition, int yPosition, int width, int height, int capacityMb, @Nullable FluidStack fluidStack) {
        if (fluidStack != null) {
            Fluid fluid = fluidStack.getFluid();
            if (fluid != null) {
                TextureAtlasSprite fluidStillSprite = getStillFluidSprite(minecraft, fluid);
                int fluidColor = fluid.getColor(fluidStack);
                int scaledAmount = (int) ((long) fluidStack.amount * (long) height) / capacityMb;
                if (fluidStack.amount > 0 && scaledAmount < 1) {
                    scaledAmount = 1;
                }

                if (scaledAmount > height) {
                    scaledAmount = height;
                }

                drawTiledSprite(minecraft, xPosition, yPosition, width, height, fluidColor, scaledAmount, fluidStillSprite);
            }
        }
    }

    private static TextureAtlasSprite getStillFluidSprite(Minecraft minecraft, Fluid fluid) {
        TextureMap textureMapBlocks = minecraft.getTextureMapBlocks();
        ResourceLocation fluidStill = fluid.getStill();
        TextureAtlasSprite fluidStillSprite = null;
        if (fluidStill != null) {
            fluidStillSprite = textureMapBlocks.getTextureExtry(fluidStill.toString());
        }

        if (fluidStillSprite == null) {
            fluidStillSprite = textureMapBlocks.getMissingSprite();
        }

        return fluidStillSprite;
    }

    private static void drawTiledSprite(Minecraft minecraft, int xPosition, int yPosition, int tiledWidth, int tiledHeight, int color, int scaledAmount, TextureAtlasSprite sprite) {
        minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        setGLColorFromInt(color);
        int xTileCount = tiledWidth / 16;
        int xRemainder = tiledWidth - xTileCount * 16;
        int yTileCount = scaledAmount / 16;
        int yRemainder = scaledAmount - yTileCount * 16;
        int yStart = yPosition + tiledHeight;

        for (int xTile = 0; xTile <= xTileCount; ++xTile) {
            for (int yTile = 0; yTile <= yTileCount; ++yTile) {
                int width = xTile == xTileCount ? xRemainder : 16;
                int height = yTile == yTileCount ? yRemainder : 16;
                int x = xPosition + xTile * 16;
                int y = yStart - (yTile + 1) * 16;
                if (width > 0 && height > 0) {
                    int maskTop = 16 - height;
                    int maskRight = 16 - width;
                    drawTextureWithMasking(x, y, sprite, maskTop, maskRight, 100.0F);
                }
            }
        }

    }

    private static void setGLColorFromInt(int color) {
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        GlStateManager.color(red, green, blue, 1.0F);
    }

    private static void drawTextureWithMasking(double xCoord, double yCoord, TextureAtlasSprite textureSprite, int maskTop, int maskRight, double zLevel) {
        double uMin = textureSprite.getMinU();
        double uMax = textureSprite.getMaxU();
        double vMin = textureSprite.getMinV();
        double vMax = textureSprite.getMaxV();
        uMax -= (double) maskRight / (double) 16.0F * (uMax - uMin);
        vMax -= (double) maskTop / (double) 16.0F * (vMax - vMin);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(xCoord, yCoord + (double) 16.0F, zLevel).tex(uMin, vMax).endVertex();
        bufferBuilder.pos(xCoord + (double) 16.0F - (double) maskRight, yCoord + (double) 16.0F, zLevel).tex(uMax, vMax).endVertex();
        bufferBuilder.pos(xCoord + (double) 16.0F - (double) maskRight, yCoord + (double) maskTop, zLevel).tex(uMax, vMin).endVertex();
        bufferBuilder.pos(xCoord, yCoord + (double) maskTop, zLevel).tex(uMin, vMin).endVertex();
        tessellator.draw();
    }

}
