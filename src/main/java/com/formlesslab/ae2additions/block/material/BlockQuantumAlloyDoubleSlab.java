package com.formlesslab.ae2additions.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockQuantumAlloyDoubleSlab extends BlockSimpleSlab {
    public BlockQuantumAlloyDoubleSlab(BlockQuantumAlloySlab singleSlab) {
        super(Material.IRON, MapColor.CYAN, singleSlab);
        this.setHardness(25.0F);
        this.setResistance(150.0F);
    }

    @Override
    public boolean isDouble() {
        return true;
    }
}
