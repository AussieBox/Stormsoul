package org.aussiebox.stormsoul.client.compat.jade;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.block.custom.LabrasteelBatteryBlock;
import org.aussiebox.stormsoul.block.custom.StormRodBlock;
import org.aussiebox.stormsoul.blockentity.custom.AbstractStormsoulBlockEntity;
import org.aussiebox.stormsoul.client.compat.jade.plugin.StoredStormsoulComponentProvider;
import org.aussiebox.stormsoul.client.compat.jade.plugin.StoredStormsoulServerDataProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin(StormsoulJadePlugin.ID)
public class StormsoulJadePlugin implements IWailaPlugin {
    public static final String ID = Stormsoul.MOD_ID;
    public static final Identifier STORED_STORMSOUL = Stormsoul.id("stored_stormsoul");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(StoredStormsoulServerDataProvider.INSTANCE, AbstractStormsoulBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(StoredStormsoulComponentProvider.INSTANCE, LabrasteelBatteryBlock.class);
        registration.registerBlockComponent(StoredStormsoulComponentProvider.INSTANCE, StormRodBlock.class);
    }
}
