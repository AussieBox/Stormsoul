package org.aussiebox.stormsoul.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.recipe.ControlledShockRecipe;
import org.aussiebox.stormsoul.rei.display.ControlledShockREIDisplay;
import org.aussiebox.stormsoul.rei.entry.BlockStateEntryDefinition;

public class ModREIPlugin implements REICommonPlugin {
    public static final CategoryIdentifier<ControlledShockREIDisplay> CONTROLLED_SHOCK = CategoryIdentifier.of(Stormsoul.id("controlled_shock"));

    @Override
    public void registerEntryTypes(EntryTypeRegistry registry) {
        registry.register(EntryTypes.BLOCK_STATE, new BlockStateEntryDefinition());
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        registry.beginFiller(ControlledShockRecipe.class)
                .fill(ControlledShockREIDisplay::new);
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(Stormsoul.id("controlled_shock"), ControlledShockREIDisplay.SERIALIZER);
    }
}
