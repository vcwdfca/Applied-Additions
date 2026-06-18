package com.formlesslab.ae2additions.block.material;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockQuantumAlloyStairs extends BlockStairs {
    public BlockQuantumAlloyStairs(IBlockState modelState) {
        super(modelState);
        this.setHardness(25.0F);
        this.setResistance(150.0F);
        this.useNeighborBrightness = true;
    }
}
