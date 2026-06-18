package com.formlesslab.ae2additions.container;

import ae2.api.config.Settings;
import ae2.api.config.YesNo;
import ae2.api.util.IConfigManager;
import ae2.container.SlotSemantics;
import ae2.container.guisync.GuiSync;
import ae2.container.implementations.UpgradeableContainer;
import com.formlesslab.ae2additions.tile.TileReactionChamber;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jspecify.annotations.NonNull;

public class ContainerReactionChamber extends UpgradeableContainer<TileReactionChamber> {
    @GuiSync(7)
    public int processingTime;
    @GuiSync(8)
    public boolean working;
    @GuiSync(9)
    public int inputFluidAmount;
    @GuiSync(10)
    public int outputFluidAmount;
    @GuiSync(11)
    public YesNo autoExport = YesNo.NO;

    public ContainerReactionChamber(InventoryPlayer playerInventory, TileReactionChamber host) {
        super(playerInventory, host);
    }

    @Override
    protected void setupInventorySlots() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                this.addSlot(new SlotItemHandler(this.getHost().getInputInventory().toItemHandler(), column + row * 3,
                        26 + column * 18, 24 + row * 18), SlotSemantics.STORAGE);
            }
        }
        this.addSlot(new SlotItemHandler(this.getHost().getOutputInventory().toItemHandler(), 0, 134, 42) {
            @Override
            public boolean isItemValid(@NonNull ItemStack stack) {
                return false;
            }
        }, SlotSemantics.STORAGE);
    }

    @Override
    protected int getPlayerInventoryTop() {
        return 102;
    }

    @Override
    protected void loadSettingsFromHost(IConfigManager cm) {
        super.loadSettingsFromHost(cm);
        this.autoExport = cm.getSetting(Settings.AUTO_EXPORT);
    }

    @Override
    protected void standardDetectAndSendChanges() {
        if (this.isServerSide()) {
            TileReactionChamber host = this.getHost();
            this.processingTime = host.getProcessingTime();
            this.working = host.isWorking();
            FluidStack inputFluid = host.getInputTank().getFluid();
            FluidStack outputFluid = host.getOutputTank().getFluid();
            this.inputFluidAmount = inputFluid == null ? 0 : inputFluid.amount;
            this.outputFluidAmount = outputFluid == null ? 0 : outputFluid.amount;
        }
        super.standardDetectAndSendChanges();
    }
}
