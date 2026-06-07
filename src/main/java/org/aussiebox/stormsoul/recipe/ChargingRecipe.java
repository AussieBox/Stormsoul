package org.aussiebox.stormsoul.recipe;

import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.recipe.inventory.ChargingInventory;

import java.util.Collections;

public class ChargingRecipe implements Recipe<ChargingInventory> {
    public static final Identifier ID = Stormsoul.id("charging");

    @Getter private final Ingredient ingredient;
    @Getter private final double stormsoul;
    @Getter private final ItemStack output;

    public ChargingRecipe(Ingredient ingredient, double stormsoul, ItemStack output) {
        this.ingredient = ingredient;
        this.stormsoul = stormsoul;
        this.output = output;
    }

    @Override
    public boolean matches(ChargingInventory input, World world) {
        BlockPos chargerPos = input.getChargerPos();
        BlockPos clampPos = input.getClampPos();
        if (!world.getBlockState(chargerPos).isOf(ModBlocks.LABRASTEEL_CHARGER) || !world.getBlockState(clampPos).isOf(ModBlocks.LABRASTEEL_CLAMP)) return false;

        return ingredient.test(input.getStack()) && input.getStormsoul() >= (stormsoul * input.getStack().getCount());
    }

    @Override
    public ItemStack craft(ChargingInventory input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<ChargingInventory>> getSerializer() {
        return ModRecipes.CHARGING_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<ChargingInventory>> getType() {
        return Type.INSTANCE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forShapeless(Collections.singletonList(ingredient));
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return ModRecipes.CHARGING_CATEGORY;
    }

    public static class Type implements RecipeType<ChargingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
    }
}