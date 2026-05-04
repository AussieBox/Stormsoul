package org.aussiebox.stormsoul.rei.entry;

import com.mojang.serialization.Codec;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.common.entry.EntrySerializer;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.type.EntryDefinition;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.rei.EntryTypes;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BlockStateEntryDefinition implements EntryDefinition<BlockState>, EntrySerializer<BlockState> {
    @Override
    public Codec<BlockState> codec() {
        return BlockState.CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, BlockState> streamCodec() {
        return PacketCodecs.registryCodec(BlockState.CODEC);
    }

    @Override
    public Class<BlockState> getValueType() {
        return BlockState.class;
    }

    @Override
    public EntryType<BlockState> getType() {
        return EntryTypes.BLOCK_STATE;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public EntryRenderer<BlockState> getRenderer() {
        return EntryRenderer.empty();
    }

    @Override
    @Nullable
    public Identifier getIdentifier(EntryStack<BlockState> entry, BlockState value) {
        return Registries.BLOCK.getId(value.getBlock());
    }

    @Override
    public boolean isEmpty(EntryStack<BlockState> entry, BlockState value) {
        return value.isAir();
    }

    @Override
    public BlockState copy(EntryStack<BlockState> entry, BlockState value) {
        return value;
    }

    @Override
    public BlockState normalize(EntryStack<BlockState> entry, BlockState value) {
        return value;
    }

    @Override
    public BlockState wildcard(EntryStack<BlockState> entry, BlockState value) {
        return value;
    }

    @Override
    public @Nullable ItemStack cheatsAs(EntryStack<BlockState> entry, BlockState value) {
        return value.getBlock().asItem().getDefaultStack();
    }

    @Override
    public long hash(EntryStack<BlockState> entry, BlockState value, ComparisonContext context) {
        return value.hashCode();
    }

    @Override
    public boolean equals(BlockState o1, BlockState o2, ComparisonContext context) {
        return o1 == o2;
    }

    @Override
    public @Nullable EntrySerializer<BlockState> getSerializer() {
        return this;
    }

    @Override
    public Text asFormattedText(EntryStack<BlockState> entry, BlockState value) {
        return value.getBlock().getName();
    }

    @Override
    public Stream<? extends TagKey<?>> getTagsFor(EntryStack<BlockState> entry, BlockState value) {
        return Stream.empty();
    }
}
