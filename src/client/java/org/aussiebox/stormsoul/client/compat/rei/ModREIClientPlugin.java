package org.aussiebox.stormsoul.client.compat.rei;

import me.shedaniel.rei.api.client.entry.renderer.EntryRendererRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.client.compat.rei.category.ChargingREICategory;
import org.aussiebox.stormsoul.client.compat.rei.category.ControlledShockREICategory;
import org.aussiebox.stormsoul.client.compat.rei.entryrenderer.BlockStateEntryRenderer;
import org.aussiebox.stormsoul.compat.rei.ModREIPlugin;
import org.aussiebox.stormsoul.compat.rei.entry.BlockStateEntryDefinition;

public class ModREIClientPlugin implements REIClientPlugin {
//    @Override
//    public void registerDisplays(DisplayRegistry registry) {
//        MinecraftServer server = GameInstance.getServer();
//        if (server == null) {
//            Stormsoul.LOGGER.warn("Could not complete REI support, server not found");
//            return;
//        }
//
//        Collection<RecipeEntry<ControlledShockRecipe>> controlledShockRecipes = server.getRecipeManager().getAllOfType(ModRecipes.CONTROLLED_SHOCK_TYPE);
//        for (RecipeEntry<ControlledShockRecipe> recipeEntry : controlledShockRecipes)
//            registry.add(new ControlledShockREIDisplay(recipeEntry.value()));
//
//        Collection<RecipeEntry<ChargingRecipe>> chargingRecipes = server.getRecipeManager().getAllOfType(ModRecipes.CHARGING_TYPE);
//        for (RecipeEntry<ChargingRecipe> recipeEntry : chargingRecipes)
//            registry.add(new ChargingREIDisplay(recipeEntry.value()));
//    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ControlledShockREICategory());
        registry.addWorkstations(ModREIPlugin.CONTROLLED_SHOCK, EntryStacks.of(Items.LIGHTNING_ROD));
        registry.add(new ChargingREICategory());
        registry.addWorkstations(ModREIPlugin.CHARGING, EntryStacks.of(ModBlocks.LABRASTEEL_CHARGER), EntryStacks.of(ModBlocks.LABRASTEEL_CLAMP));
    }

    @Override
    public void registerEntryRenderers(EntryRendererRegistry registry) {
        BlockStateEntryDefinition.setRenderer(new BlockStateEntryRenderer());
//        registry.register(EntryTypes.BLOCK_STATE, (entry, last) -> new BlockStateEntryRenderer());
    }

//    @Override
//    public void registerEntries(EntryRegistry registry) {
//        for (Block block : Registries.BLOCK) {
//            for (BlockState state : block.getStateManager().getStates()) {
//                registry.addEntries(EntryStack.of(EntryTypes.BLOCK_STATE, state));
//            }
//        }
//
//        registry.removeEntryIf(entry -> entry.getType() == EntryTypes.BLOCK_STATE);
//    }

//    @Override
//    public void registerBasicEntryFiltering(BasicFilteringRule<?> rule) {
//        rule.hide(() -> {
//            EntryIngredient.Builder builder = EntryIngredient.builder();
//            for (Block block : Registries.BLOCK) {
//                block.getStateManager().getStates().forEach(blockState -> builder.add(EntryStack.of(EntryTypes.BLOCK_STATE, blockState)));
//            }
//            return builder.build();
//        });
//    }
}
