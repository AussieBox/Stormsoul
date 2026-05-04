package org.aussiebox.stormsoul.client.rei;

import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule;
import me.shedaniel.rei.api.client.entry.renderer.EntryRendererRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import org.aussiebox.stormsoul.client.rei.category.ControlledShockREICategory;
import org.aussiebox.stormsoul.client.rei.entryrenderer.BlockStateEntryRenderer;
import org.aussiebox.stormsoul.recipe.ControlledShockRecipe;
import org.aussiebox.stormsoul.rei.EntryTypes;
import org.aussiebox.stormsoul.rei.display.ControlledShockREIDisplay;

public class ModREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.beginFiller(ControlledShockRecipe.class)
                .fill(ControlledShockREIDisplay::new);
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ControlledShockREICategory());
    }

    @Override
    public void registerEntryRenderers(EntryRendererRegistry registry) {
        registry.register(EntryTypes.BLOCK_STATE, (entry, last) -> new BlockStateEntryRenderer());
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        for (Block block : Registries.BLOCK) {
            for (BlockState state : block.getStateManager().getStates()) {
                registry.addEntries(EntryStack.of(EntryTypes.BLOCK_STATE, state));
            }
        }
    }

    @Override
    public void registerBasicEntryFiltering(BasicFilteringRule<?> rule) {
        rule.hide(() -> {
            EntryIngredient.Builder builder = EntryIngredient.builder();
            for (Block block : Registries.BLOCK) {
                block.getStateManager().getStates().forEach(blockState -> builder.add(EntryStack.of(EntryTypes.BLOCK_STATE, blockState)));
            }
            return builder.build();
        });
    }
}
