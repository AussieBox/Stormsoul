package org.aussiebox.stormsoul.recipe.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import org.aussiebox.stormsoul.recipe.ChargingRecipe;

public class ChargingRecipeSerializer implements RecipeSerializer<ChargingRecipe> {
    public static final ChargingRecipeSerializer INSTANCE = new ChargingRecipeSerializer();

    public static final MapCodec<ChargingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(ChargingRecipe::getIngredient),
            Codec.DOUBLE.fieldOf("stormsoul").forGetter(ChargingRecipe::getStormsoul),
            ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(ChargingRecipe::getOutput)
    ).apply(instance, ChargingRecipe::new));

    public static final PacketCodec<RegistryByteBuf, ChargingRecipe> PACKET_CODEC = PacketCodec.tuple(
            Ingredient.PACKET_CODEC,
            ChargingRecipe::getIngredient,

            PacketCodecs.DOUBLE,
            ChargingRecipe::getStormsoul,

            ItemStack.PACKET_CODEC,
            ChargingRecipe::getOutput,

            ChargingRecipe::new
    );

    @Override
    public MapCodec<ChargingRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, ChargingRecipe> packetCodec() {
        return PACKET_CODEC;
    }
}
