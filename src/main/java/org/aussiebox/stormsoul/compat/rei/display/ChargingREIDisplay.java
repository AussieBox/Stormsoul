package org.aussiebox.stormsoul.compat.rei.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.aussiebox.stormsoul.compat.rei.ModREIPlugin;
import org.aussiebox.stormsoul.recipe.ChargingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChargingREIDisplay extends BasicDisplay {
    public static final DisplaySerializer<ChargingREIDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(ChargingREIDisplay::getInputEntries),
                    EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(ChargingREIDisplay::getOutputEntries),
                    Codec.DOUBLE.fieldOf("stormsoul").forGetter(d -> d.stormsoul)
            ).apply(instance, ChargingREIDisplay::new)),
            PacketCodec.tuple(
                    EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
                    ChargingREIDisplay::getInputEntries,
                    EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
                    ChargingREIDisplay::getOutputEntries,
                    PacketCodecs.DOUBLE,
                    display -> display.stormsoul,
                    ChargingREIDisplay::new
            )
    );

    @Getter private final double stormsoul;

    public ChargingREIDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, double stormsoul) {
        super(inputs, outputs);
        this.stormsoul = stormsoul;
    }

    public ChargingREIDisplay(ChargingRecipe recipe) {
        this(getInputs(recipe), getOutputs(recipe), recipe.getStormsoul());
    }

    public static List<EntryIngredient> getInputs(ChargingRecipe recipe) {
        return new ArrayList<>(EntryIngredients.ofIngredients(Collections.singletonList(recipe.getIngredient())));
    }

    private static List<EntryIngredient> getOutputs(ChargingRecipe recipe) {
        return List.of(EntryIngredients.of(recipe.getOutput()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ModREIPlugin.CHARGING;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
