package com.formlesslab.ae2additions.client.gui;

import ae2.api.config.Settings;
import ae2.api.config.YesNo;
import ae2.client.Point;
import ae2.client.gui.Icon;
import ae2.client.gui.implementations.GuiUpgradeable;
import ae2.client.gui.style.Blitter;
import ae2.client.gui.style.GuiStyle;
import ae2.client.gui.style.WidgetStyle;
import ae2.client.gui.widgets.IconButton;
import ae2.client.gui.widgets.ServerSettingToggleButton;
import ae2.container.GuiIds;
import ae2.core.localization.ButtonToolTips;
import ae2.core.network.InitNetwork;
import ae2.core.network.serverbound.SwitchGuisPacket;
import com.formlesslab.ae2additions.container.ContainerReactionChamber;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;
import java.util.List;

public class GuiReactionChamber extends GuiUpgradeable<ContainerReactionChamber> {
    private final ServerSettingToggleButton<YesNo> autoExportButton;
    private final IconButton outputSidesButton;

    public GuiReactionChamber(ContainerReactionChamber container, InventoryPlayer playerInventory, GuiStyle style) {
        super(container, playerInventory, new TextComponentTranslation("gui.ae2additions.reaction_chamber"), style);
        this.xSize = 176;
        this.ySize = 184;
        this.autoExportButton = this.addToLeftToolbar(new ServerSettingToggleButton<>(Settings.AUTO_EXPORT, YesNo.NO));
        this.outputSidesButton = this.addToLeftToolbar(new IconButton(this::openOutputSides) {
            {
                this.setMessage(ButtonToolTips.OutputSideConfig.text());
            }

            @Override
            protected Icon getIcon() {
                return Icon.OUTPUT_SIDE_CONFIG;
            }

            @Override
            public List<ITextComponent> getTooltipMessage() {
                return List.of(ButtonToolTips.OutputSideConfig.text(), ButtonToolTips.OutputSideConfigHint.text());
            }
        });
    }

    @Override
    protected void updateBeforeRender() {
        super.updateBeforeRender();
        this.autoExportButton.set(this.container.autoExport);
        this.outputSidesButton.setVisibility(this.container.autoExport == YesNo.YES);
        this.setTextContent("inputFluidAmount", new TextComponentString(this.container.inputFluidAmount + " mB"));
        this.setTextContent("outputFluidAmount", new TextComponentString(this.container.outputFluidAmount + " mB"));
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY, float partialTicks) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY, partialTicks);
        int progress = Math.min(24, this.container.processingTime * 24 / TileProgress.MAX);
        if (progress > 0) {
            WidgetStyle widget = this.style.getWidget("progressBar");
            Point pos = widget.resolve(new Rectangle(0, 0, this.xSize, this.ySize));
            Blitter progressBar = this.style.getImage("progressBar");
            progressBar.srcWidth(progress).dest(offsetX + pos.x(), offsetY + pos.y()).blit();
        }
    }

    private void openOutputSides() {
        InitNetwork.sendToServer(SwitchGuisPacket.openSubGui(GuiIds.GuiKey.OUTPUT_SIDES));
    }

    private static final class TileProgress {
        private static final int MAX = 200;
    }
}
