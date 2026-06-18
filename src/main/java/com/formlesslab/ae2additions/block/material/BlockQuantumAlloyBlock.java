package com.formlesslab.ae2additions.block.material;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockQuantumAlloyBlock extends Block {
    public BlockQuantumAlloyBlock() {
        super(Material.IRON);
        this.setHardness(25.0F);
        this.setResistance(150.0F);
        this.setSoundType(SoundType.METAL);
    }
}
