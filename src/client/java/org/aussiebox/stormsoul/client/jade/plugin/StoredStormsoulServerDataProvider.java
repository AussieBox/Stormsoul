package org.aussiebox.stormsoul.client.jade.plugin;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.aussiebox.stormsoul.blockentity.custom.AbstractStormsoulBlockEntity;
import org.aussiebox.stormsoul.client.jade.StormsoulJadePlugin;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public class StoredStormsoulServerDataProvider implements IServerDataProvider<BlockAccessor> {
    public static final StoredStormsoulServerDataProvider INSTANCE = new StoredStormsoulServerDataProvider();

    @Override
    public void appendServerData(NbtCompound tag, BlockAccessor blockAccessor) {
        AbstractStormsoulBlockEntity entity = (AbstractStormsoulBlockEntity) blockAccessor.getBlockEntity();
        tag.putDouble("maxStoredStormsoul", entity.getMaxStoredStormsoul());
        tag.putDouble("storedStormsoul", entity.getStoredStormsoul());
        tag.putDouble("transferPerTick", entity.getTransferPerTick());
        tag.put("inputDirections", Codec.list(Direction.CODEC), entity.getInputDirections());
        tag.put("outputDirections", Codec.list(Direction.CODEC), entity.getOutputDirections());
        tag.put("transferDirections", Codec.list(Direction.CODEC), entity.getTransferDirections());
        tag.put("transferMethod", AbstractStormsoulBlockEntity.TransferMethod.CODEC, entity.getTransferMethod());
    }

    @Override
    public Identifier getUid() {
        return StormsoulJadePlugin.STORED_STORMSOUL;
    }
}
