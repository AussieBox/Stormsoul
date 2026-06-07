package org.aussiebox.stormsoul.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.aussiebox.stormsoul.recipe.serializer.ChargingRecipeSerializer;
import org.aussiebox.stormsoul.recipe.serializer.ControlledShockRecipeSerializer;

public class ModRecipes {
    public static final RecipeBookCategory CONTROLLED_SHOCK_CATEGORY = Registry.register(
            Registries.RECIPE_BOOK_CATEGORY,
            ControlledShockRecipe.ID,
            new RecipeBookCategory()
    );

    public static final RecipeType<ControlledShockRecipe> CONTROLLED_SHOCK_TYPE = Registry.register(
            Registries.RECIPE_TYPE,
            ControlledShockRecipe.ID,
            ControlledShockRecipe.Type.INSTANCE
    );

    public static final RecipeSerializer<ControlledShockRecipe> CONTROLLED_SHOCK_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            ControlledShockRecipe.ID,
            ControlledShockRecipeSerializer.INSTANCE
    );

    public static final RecipeBookCategory CHARGING_CATEGORY = Registry.register(
            Registries.RECIPE_BOOK_CATEGORY,
            ChargingRecipe.ID,
            new RecipeBookCategory()
    );

    public static final RecipeType<ChargingRecipe> CHARGING_TYPE = Registry.register(
            Registries.RECIPE_TYPE,
            ChargingRecipe.ID,
            ChargingRecipe.Type.INSTANCE
    );

    public static final RecipeSerializer<ChargingRecipe> CHARGING_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            ChargingRecipe.ID,
            ChargingRecipeSerializer.INSTANCE
    );

    public static void init() {

    }
}
