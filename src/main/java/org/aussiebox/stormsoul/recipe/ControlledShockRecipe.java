package org.aussiebox.stormsoul.recipe;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableInt;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.recipe.inventory.ControlledShockInventory;
import org.aussiebox.stormsoul.recipe.serializer.ControlledShockRecipeSerializer;
import org.aussiebox.stormsoul.util.StormsoulUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControlledShockRecipe implements Recipe<ControlledShockInventory> {
    public static final Identifier ID = Stormsoul.id("controlled_shock");

    @Getter private final List<Ingredient> ingredients;
    @Getter private final List<BlockState> belowBlocks;
    @Getter private final ItemStack output;

    public ControlledShockRecipe(List<Ingredient> ingredients, List<BlockState> belowBlocks, ItemStack output) {
        this.ingredients = ingredients;
        this.belowBlocks = belowBlocks;
        this.output = output;
    }

    @Override
    public boolean matches(ControlledShockInventory input, World world) {
        BlockPos pos = input.getRodPos();
        if (!world.getBlockState(pos).isOf(Blocks.LIGHTNING_ROD)) {
            Stormsoul.LOGGER.info("not lightning rod");
            return false;
        }

        List<BlockState> belowStates = new ArrayList<>(input.getBelowBlocks());
        List<BlockState> checkBelowStates = new ArrayList<>(belowBlocks);
        for (BlockState state : belowStates)
            if (!checkBelowStates.contains(state)) {
                Stormsoul.LOGGER.info("below states failed");
                return false;
            }
            else checkBelowStates.remove(state);

        List<Pair<Item, MutableInt>> stacks = StormsoulUtil.condenseStacks(new ArrayList<>(input.getStacks()));
        if (stacks.isEmpty()) {
            Stormsoul.LOGGER.info("stacks empty");
            return false;
        }

        List<Ingredient> checkBelowStacks = new ArrayList<>(ingredients);
        for (Pair<Item, MutableInt> pair : stacks) {
            Item item = pair.getLeft();
            int count = pair.getRight().getValue();
            Stormsoul.LOGGER.info("{}x {}", count, item);
            boolean pass = false;
            for (Ingredient ingredient : checkBelowStacks) {
                if (ingredient.test(new ItemStack(item))) {
                    if (count >= Collections.frequency(checkBelowStacks, ingredient)) {
                        pass = true;
                        break;
                    }
                }
            }
            if (!pass) return false;
        }

        return true;
    }

    @Override
    public ItemStack craft(ControlledShockInventory input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<ControlledShockInventory>> getSerializer() {
        return ControlledShockRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<ControlledShockInventory>> getType() {
        return Type.INSTANCE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forShapeless(ingredients);
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return ModRecipes.CONTROLLED_SHOCK_CATEGORY;
    }

    public static class Type implements RecipeType<ControlledShockRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
    }
}