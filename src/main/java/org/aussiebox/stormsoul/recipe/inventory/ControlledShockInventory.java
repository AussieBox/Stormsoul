package org.aussiebox.stormsoul.recipe.inventory;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.math.BlockPos;
import org.aussiebox.stormsoul.recipe.ControlledShockRecipe;

import java.util.List;
import java.util.function.Predicate;

public class ControlledShockInventory implements RecipeInput {
    @Getter private final List<ItemStack> stacks;
    @Getter private final List<BlockState> belowBlocks;
    @Getter private final BlockPos rodPos;
    @Getter private final RecipeMatcher<ControlledShockRecipe> recipeMatcher = new RecipeMatcher<>();

    public ControlledShockInventory(List<ItemStack> stacks, List<BlockState> belowBlocks, BlockPos rodPos) {
        this.stacks = stacks;
        this.belowBlocks = belowBlocks;
        this.rodPos = rodPos;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot >= stacks.size()) return ItemStack.EMPTY;
        return stacks.get(slot);
    }

    @Override
    public int size() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        return stacks.isEmpty() && belowBlocks.stream().allMatch(Predicate.isEqual(Blocks.AIR.getDefaultState()));
    }
}
