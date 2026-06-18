package com.formlesslab.ae2additions.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockQuantumAlloySlab extends BlockSimpleSlab {
    public BlockQuantumAlloySlab() {
        super(Material.IRON, MapColor.CYAN, null);
        this.setHardness(25.0F);
        this.setResistance(150.0F);
    }

    @Override
    public boolean isDouble() {
        return false;
    }
}
