package com.formlesslab.ae2additions;

import ae2.client.gui.style.GuiStyleManager;
import com.formlesslab.ae2additions.client.gui.GuiWirelessConnector;
import com.formlesslab.ae2additions.client.gui.GuiWirelessHub;
import com.formlesslab.ae2additions.container.ContainerWirelessConnector;
import com.formlesslab.ae2additions.container.ContainerWirelessHub;
import com.formlesslab.ae2additions.tile.TileWirelessConnector;
import com.formlesslab.ae2additions.tile.TileWirelessHub;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModGuiHandler implements IGuiHandler {
    public static final int WIRELESS_CONNECTOR = 0;
    public static final int WIRELESS_HUB = 1;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        if (id == WIRELESS_CONNECTOR && tile instanceof TileWirelessConnector connector) {
            return new ContainerWirelessConnector(player.inventory, connector);
        }
        if (id == WIRELESS_HUB && tile instanceof TileWirelessHub hub) {
            return new ContainerWirelessHub(player.inventory, hub);
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        if (id == WIRELESS_CONNECTOR && tile instanceof TileWirelessConnector connector) {
            return new GuiWirelessConnector(
                new ContainerWirelessConnector(player.inventory, connector),
                player.inventory,
                GuiStyleManager.loadStyleDoc("/screens/wireless_connector.json"));
        }
        if (id == WIRELESS_HUB && tile instanceof TileWirelessHub hub) {
            return new GuiWirelessHub(
                new ContainerWirelessHub(player.inventory, hub),
                player.inventory,
                GuiStyleManager.loadStyleDoc("/screens/wireless_hub.json"));
        }
        return null;
    }
}
