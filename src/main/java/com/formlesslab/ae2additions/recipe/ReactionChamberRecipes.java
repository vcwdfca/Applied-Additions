package com.formlesslab.ae2additions.recipe;

import ae2.util.inv.AppEngInternalInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.HashSet;
import java.util.Set;

public final class ReactionChamberRecipes {

    public static Set<ReactionChamberRecipe> REACTION_CHAMBER_RECIPES = new HashSet<>();

    private ReactionChamberRecipes() {
    }

    public static ReactionChamberRecipe findRecipe(AppEngInternalInventory inventory, FluidTank inputTank, ItemStack output, FluidTank outputTank, int outputTankCapacity) {
        for (ReactionChamberRecipe recipe : REACTION_CHAMBER_RECIPES) {
            if (recipe.matches(inventory, inputTank) && recipe.canOutput(output, outputTank, outputTankCapacity)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isValidIngredient(ItemStack stack) {
        for (ReactionChamberRecipe recipe : REACTION_CHAMBER_RECIPES) {
            if (recipe.containsIngredient(stack)) {
                return true;
            }
        }
        return false;
    }

    public static Set<ReactionChamberRecipe> getRecipes() {
        return REACTION_CHAMBER_RECIPES;
    }
}
