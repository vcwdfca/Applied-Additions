package com.formlesslab.ae2additions.init;

import ae2.api.upgrades.Upgrades;
import ae2.core.definitions.AEItems;
import net.minecraft.item.Item;

public final class ModUpgrades {
    private ModUpgrades() {
    }

    public static void init() {
        Upgrades.add(AEItems.ENERGY_CARD.item(), Item.getItemFromBlock(ModContent.WIRELESS_CONNECTOR), 4);
        Upgrades.add(AEItems.ENERGY_CARD.item(), Item.getItemFromBlock(ModContent.WIRELESS_HUB), 4);
        Upgrades.add(AEItems.SPEED_CARD.item(), Item.getItemFromBlock(ModContent.REACTION_CHAMBER), 4);
        Upgrades.add(AEItems.PARALLEL_CARD.item(), Item.getItemFromBlock(ModContent.REACTION_CHAMBER), 3);
    }
}
