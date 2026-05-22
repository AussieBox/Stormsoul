package org.aussiebox.stormsoul.client.geckolib.model.item;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.item.custom.geckolib.StormRodItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class StormRodItemModel extends GeoModel<StormRodItem> {
    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("block/storm_rod");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("textures/block/storm_rod.png");
    }

    @Override
    public Identifier getAnimationResource(StormRodItem stormRodItem) {
        return Stormsoul.id("block/storm_rod");
    }
}
