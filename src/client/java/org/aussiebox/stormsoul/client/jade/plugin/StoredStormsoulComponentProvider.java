package org.aussiebox.stormsoul.client.jade.plugin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.client.jade.StormsoulJadePlugin;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class StoredStormsoulComponentProvider implements IBlockComponentProvider {
    public static final StoredStormsoulComponentProvider INSTANCE = new StoredStormsoulComponentProvider();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        NbtCompound tag = blockAccessor.getServerData();
        double stored = tag.getDouble("storedStormsoul", 0);
        double max = tag.getDouble("maxStoredStormsoul", 0);
        if (tag.contains("storedStormsoul")) iTooltip.add(Text.translatable("tooltip.stormsoul.stored_stormsoul", stored, max).withColor(0xAAAAAA));
    }

    @Override
    public Identifier getUid() {
        return StormsoulJadePlugin.STORED_STORMSOUL;
    }
}
