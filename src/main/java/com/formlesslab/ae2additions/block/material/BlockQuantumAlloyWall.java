package com.formlesslab.ae2additions.block.material;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class BlockQuantumAlloyWall extends BlockWall {
    public BlockQuantumAlloyWall(Block modelBlock) {
        super(modelBlock);
        this.setHardness(25.0F);
        this.setResistance(150.0F);
        this.useNeighborBrightness = true;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }
}
