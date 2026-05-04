package org.aussiebox.stormsoul.recipe.serializer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import org.aussiebox.stormsoul.recipe.ControlledShockRecipe;

public class ControlledShockRecipeSerializer implements RecipeSerializer<ControlledShockRecipe> {
    public static final ControlledShockRecipeSerializer INSTANCE = new ControlledShockRecipeSerializer();

    public static final MapCodec<ControlledShockRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(ControlledShockRecipe::getIngredients),
            BlockState.CODEC.listOf().fieldOf("blocks_below").forGetter(ControlledShockRecipe::getBelowBlocks),
            ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(ControlledShockRecipe::getOutput)
    ).apply(instance, ControlledShockRecipe::new));

    public static final PacketCodec<RegistryByteBuf, ControlledShockRecipe> PACKET_CODEC = PacketCodec.tuple(
            Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()),
            recipe -> recipe.getIngredients().stream().toList(),

            PacketCodecs.registryCodec(BlockState.CODEC).collect(PacketCodecs.toList()),
            recipe -> recipe.getBelowBlocks().stream().toList(),

            ItemStack.PACKET_CODEC,
            ControlledShockRecipe::getOutput,

            ControlledShockRecipe::new
    );

    @Override
    public MapCodec<ControlledShockRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, ControlledShockRecipe> packetCodec() {
        return PACKET_CODEC;
    }
}
