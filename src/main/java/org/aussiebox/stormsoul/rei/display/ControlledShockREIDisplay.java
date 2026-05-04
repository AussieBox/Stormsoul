package org.aussiebox.stormsoul.rei.display;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.aussiebox.stormsoul.recipe.ControlledShockRecipe;
import org.aussiebox.stormsoul.rei.EntryTypes;
import org.aussiebox.stormsoul.rei.ModREIPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ControlledShockREIDisplay extends BasicDisplay {
    public static final DisplaySerializer<ControlledShockREIDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(ControlledShockREIDisplay::getInputEntries),
                    EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(ControlledShockREIDisplay::getOutputEntries)
            ).apply(instance, ControlledShockREIDisplay::new)),
            PacketCodec.tuple(
                    EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
                    ControlledShockREIDisplay::getInputEntries,
                    EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
                    ControlledShockREIDisplay::getOutputEntries,
                    ControlledShockREIDisplay::new
            )
    );

    public ControlledShockREIDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ControlledShockREIDisplay(ControlledShockRecipe recipe) {
        this(getInputs(recipe), getOutputs(recipe));
    }

    private static List<EntryIngredient> getInputs(ControlledShockRecipe recipe) {
        List<EntryIngredient> inputs = new ArrayList<>(EntryIngredients.ofIngredients(recipe.getIngredients()));
        recipe.getBelowBlocks().forEach(blockState ->
                inputs.add(EntryIngredient.of(EntryStack.of(EntryTypes.BLOCK_STATE, blockState)))
        );
        return inputs;
    }

    private static List<EntryIngredient> getOutputs(ControlledShockRecipe recipe) {
        return List.of(EntryIngredients.of(recipe.getOutput()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ModREIPlugin.CONTROLLED_SHOCK;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
