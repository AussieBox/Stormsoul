package org.aussiebox.stormsoul.compat.rei;

import dev.architectury.utils.GameInstance;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.server.MinecraftServer;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.compat.rei.display.ChargingREIDisplay;
import org.aussiebox.stormsoul.compat.rei.display.ControlledShockREIDisplay;
import org.aussiebox.stormsoul.compat.rei.entry.BlockStateEntryDefinition;
import org.aussiebox.stormsoul.recipe.ChargingRecipe;
import org.aussiebox.stormsoul.recipe.ControlledShockRecipe;
import org.aussiebox.stormsoul.recipe.ModRecipes;

import java.util.Collection;

public class ModREIPlugin implements REICommonPlugin {
    public static final CategoryIdentifier<ControlledShockREIDisplay> CONTROLLED_SHOCK = CategoryIdentifier.of(Stormsoul.id("controlled_shock"));
    public static final CategoryIdentifier<ChargingREIDisplay> CHARGING = CategoryIdentifier.of(Stormsoul.id("charging"));

    @Override
    public void registerEntryTypes(EntryTypeRegistry registry) {
        registry.register(EntryTypes.BLOCK_STATE, new BlockStateEntryDefinition());
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        MinecraftServer server = GameInstance.getServer();
        if (server == null) {
            Stormsoul.LOGGER.warn("Could not complete REI support, server not found");
            return;
        }

        Collection<RecipeEntry<ControlledShockRecipe>> controlledShockRecipes = server.getRecipeManager().getAllOfType(ModRecipes.CONTROLLED_SHOCK_TYPE);
        for (RecipeEntry<ControlledShockRecipe> recipeEntry : controlledShockRecipes)
            registry.add(new ControlledShockREIDisplay(recipeEntry.value()));

        Collection<RecipeEntry<ChargingRecipe>> chargingRecipes = server.getRecipeManager().getAllOfType(ModRecipes.CHARGING_TYPE);
        for (RecipeEntry<ChargingRecipe> recipeEntry : chargingRecipes)
            registry.add(new ChargingREIDisplay(recipeEntry.value()));
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(Stormsoul.id("controlled_shock"), ControlledShockREIDisplay.SERIALIZER);
        registry.register(Stormsoul.id("charging"), ChargingREIDisplay.SERIALIZER);
    }
}
