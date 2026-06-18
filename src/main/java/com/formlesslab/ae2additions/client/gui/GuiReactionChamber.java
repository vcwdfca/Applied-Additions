package com.formlesslab.ae2additions.client.gui;

import ae2.api.config.Settings;
import ae2.api.config.YesNo;
import ae2.client.gui.Icon;
import ae2.client.gui.implementations.GuiUpgradeable;
import ae2.client.gui.style.FluidBlitter;
import ae2.client.gui.style.GuiStyle;
import ae2.client.gui.widgets.IconButton;
import ae2.client.gui.widgets.ProgressBar;
import ae2.client.gui.widgets.ServerSettingToggleButton;
import ae2.core.localization.ButtonToolTips;
import com.formlesslab.ae2additions.container.ContainerReactionChamber;
import com.formlesslab.ae2additions.network.CReactionChamberOutputSides;
import com.formlesslab.ae2additions.network.ModNetwork;
import com.formlesslab.ae2additions.tile.TileReactionChamber;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiReactionChamber extends GuiUpgradeable<ContainerReactionChamber> {
    private static final Rectangle INPUT_TANK = new Rectangle(9, 21, 16, 58);
    private static final Rectangle OUTPUT_TANK = new Rectangle(151, 21, 16, 58);
    private final ProgressBar progressBar;
    private final ServerSettingToggleButton<YesNo> autoExportButton;
    private final IconButton outputSidesButton;

    public GuiReactionChamber(ContainerReactionChamber container, InventoryPlayer playerInventory, GuiStyle style) {
        super(container, playerInventory, new TextComponentTranslation("gui.ae2additions.reaction_chamber"), style);
        this.xSize = 176;
        this.ySize = 184;
        this.progressBar = new ProgressBar(this.container, style.getImage("progressBar"), ProgressBar.Direction.VERTICAL);
        this.widgets.add("progressBar", this.progressBar);
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
        int maxProgress = this.container.getMaxProgress();
        int progress = maxProgress > 0 ? this.container.getCurrentProgress() * 100 / maxProgress : 0;
        this.progressBar.setFullMsg(new TextComponentString(progress + "%"));
        this.autoExportButton.set(this.container.getAutoExport());
        this.outputSidesButton.setVisibility(this.container.getAutoExport() == YesNo.YES);
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY, float partialTicks) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY, partialTicks);
        this.drawFluidTank(offsetX, offsetY, this.container.getHost().getInputTank().getFluid(),
                this.container.inputFluidAmount, INPUT_TANK);
        this.drawFluidTank(offsetX, offsetY, this.container.getHost().getOutputTank().getFluid(),
                this.container.outputFluidAmount, OUTPUT_TANK);
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        if (this.drawTankTooltip(mouseX, mouseY, INPUT_TANK, this.container.getHost().getInputTank().getFluid(),
                this.container.inputFluidAmount)) {
            return;
        }
        if (this.drawTankTooltip(mouseX, mouseY, OUTPUT_TANK, this.container.getHost().getOutputTank().getFluid(),
                this.container.outputFluidAmount)) {
            return;
        }
        super.renderHoveredToolTip(mouseX, mouseY);
    }

    private void openOutputSides() {
        ModNetwork.sendToServer(new CReactionChamberOutputSides());
    }

    private void drawFluidTank(int offsetX, int offsetY, FluidStack fluid, int amount, Rectangle tank) {
        if (fluid == null || amount <= 0) {
            return;
        }

        int fillHeight = Math.max(1, tank.height * amount / TileReactionChamber.TANK_CAPACITY);
        int drawY = tank.y + tank.height - fillHeight;
        FluidStack renderStack = fluid.copy();
        renderStack.amount = Math.max(1, amount);
        FluidBlitter.create(renderStack)
                .dest(offsetX + tank.x, offsetY + drawY, tank.width, fillHeight)
                .blit();
    }

    private boolean drawTankTooltip(int mouseX, int mouseY, Rectangle tank, FluidStack fluid, int amount) {
        int relX = mouseX - this.guiLeft;
        int relY = mouseY - this.guiTop;
        if (!tank.contains(relX, relY) || fluid == null || amount <= 0) {
            return false;
        }

        List<ITextComponent> tooltip = new ArrayList<>();
        tooltip.add(new TextComponentString(fluid.getFluid().getLocalizedName(new FluidStack(fluid.getFluid(), 1))));
        tooltip.add(new TextComponentString(amount + " / " + TileReactionChamber.TANK_CAPACITY + " mB"));
        this.drawTooltipWithHeader(mouseX, mouseY, tooltip);
        return true;
    }
}
