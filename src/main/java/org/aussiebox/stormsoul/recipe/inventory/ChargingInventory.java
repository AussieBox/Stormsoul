package org.aussiebox.stormsoul.recipe.inventory;

import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.math.BlockPos;
import org.aussiebox.stormsoul.recipe.ChargingRecipe;

public class ChargingInventory implements RecipeInput {
    @Getter private final ItemStack stack;
    @Getter private final double stormsoul;
    @Getter private final BlockPos chargerPos;
    @Getter private final BlockPos clampPos;
    @Getter private final RecipeMatcher<ChargingRecipe> recipeMatcher = new RecipeMatcher<>();

    public ChargingInventory(ItemStack stack, double stormsoul, BlockPos chargerPos, BlockPos clampPos) {
        this.stack = stack;
        this.stormsoul = stormsoul;
        this.chargerPos = chargerPos;
        this.clampPos = clampPos;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return stack;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
