package com.formlesslab.ae2additions.init;

import com.formlesslab.ae2additions.Reference;
import com.formlesslab.ae2additions.block.BlockWirelessConnector;
import com.formlesslab.ae2additions.block.BlockWirelessHub;
import com.formlesslab.ae2additions.item.ItemWirelessConnectorUpgrade;
import com.formlesslab.ae2additions.item.ItemWirelessTool;
import com.formlesslab.ae2additions.tile.TileWirelessConnector;
import com.formlesslab.ae2additions.tile.TileWirelessHub;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public final class ModContent {
    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(WIRELESS_CONNECTOR);
        }
    };

    public static final BlockWirelessConnector WIRELESS_CONNECTOR = new BlockWirelessConnector();
    public static final BlockWirelessHub WIRELESS_HUB = new BlockWirelessHub();
    public static final ItemWirelessTool WIRELESS_TOOL = new ItemWirelessTool();
    public static final ItemWirelessConnectorUpgrade WIRELESS_CONNECTOR_UPGRADE = new ItemWirelessConnectorUpgrade();

    private ModContent() {
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileWirelessConnector.class, id("wireless_connect"));
        GameRegistry.registerTileEntity(TileWirelessHub.class, id("wireless_hub"));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(Reference.MOD_ID, path);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
            setupBlock(WIRELESS_CONNECTOR, "wireless_connect"),
            setupBlock(WIRELESS_HUB, "wireless_hub")
        );
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            setupBlockItem(WIRELESS_CONNECTOR, "wireless_connect"),
            setupBlockItem(WIRELESS_HUB, "wireless_hub"),
            setupItem(WIRELESS_TOOL, "wireless_tool"),
            setupItem(WIRELESS_CONNECTOR_UPGRADE, "wireless_connector_upgrade")
        );
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerModel(Item.getItemFromBlock(WIRELESS_CONNECTOR), "wireless_connect");
        registerModel(Item.getItemFromBlock(WIRELESS_HUB), "wireless_hub");
        registerModel(WIRELESS_TOOL, "wireless_tool");
        registerModel(WIRELESS_CONNECTOR_UPGRADE, "wireless_connector_upgrade");
    }

    private static <T extends Block> T setupBlock(T block, String name) {
        block.setRegistryName(id(name));
        block.setTranslationKey(Reference.MOD_ID + "." + name);
        block.setCreativeTab(CREATIVE_TAB);
        block.setHardness(2.2F);
        block.setResistance(10.0F);
        return block;
    }

    private static Item setupBlockItem(Block block, String name) {
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(id(name));
        item.setTranslationKey(Reference.MOD_ID + "." + name);
        return item;
    }

    private static <T extends Item> T setupItem(T item, String name) {
        item.setRegistryName(id(name));
        item.setTranslationKey(Reference.MOD_ID + "." + name);
        item.setCreativeTab(CREATIVE_TAB);
        return item;
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item, String name) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
            new ModelResourceLocation(id(name), "inventory"));
    }
}
