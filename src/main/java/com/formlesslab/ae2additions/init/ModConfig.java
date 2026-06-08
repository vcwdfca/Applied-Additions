package com.formlesslab.ae2additions.init;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public final class ModConfig {
    public static double wirelessConnectorMaxRange = 1000.0;
    public static double wirelessConnectorPowerMultiplier = 1.0;

    private ModConfig() {
    }

    public static void init(File file) {
        Configuration config = new Configuration(file);
        try {
            config.load();
            wirelessConnectorMaxRange = config.getFloat(
                "wireless_connector_max_range",
                "device",
                1000.0F,
                10.0F,
                10000.0F,
                "Maximum wireless connector range in blocks.");
            wirelessConnectorPowerMultiplier = config.getFloat(
                "wireless_connector_power_multiplier",
                "device",
                1.0F,
                0.0F,
                100.0F,
                "Power multiplier for wireless connector idle drain.");
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}
