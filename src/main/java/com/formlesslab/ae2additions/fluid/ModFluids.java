package com.formlesslab.ae2additions.fluid;

import com.formlesslab.ae2additions.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public final class ModFluids {
    public static final int QUANTUM_INFUSION_COLOR = 0xFF7362D3;
    private static final ResourceLocation WATER_STILL = new ResourceLocation("minecraft", "blocks/water_still");
    private static final ResourceLocation WATER_FLOW = new ResourceLocation("minecraft", "blocks/water_flow");

    public static final Fluid QUANTUM_INFUSION = new Fluid(
            "quantum_infusion",
            WATER_STILL,
            WATER_FLOW
    ).setColor(QUANTUM_INFUSION_COLOR)
            .setDensity(300)
            .setViscosity(1000);

    private ModFluids() {
    }

    public static void init() {
        FluidRegistry.registerFluid(QUANTUM_INFUSION);
        FluidRegistry.addBucketForFluid(QUANTUM_INFUSION);
    }

    public static class QuantumInfusionBlock extends BlockFluidClassic {
        public QuantumInfusionBlock() {
            super(QUANTUM_INFUSION, Material.WATER);
            this.setQuantaPerBlock(4);
            this.setLightOpacity(2);
        }

        @Override
        public String getTranslationKey() {
            return "tile." + Reference.MOD_ID + ".quantum_infusion_block";
        }
    }
}
