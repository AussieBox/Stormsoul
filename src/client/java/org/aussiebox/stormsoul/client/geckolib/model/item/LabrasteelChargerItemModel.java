package org.aussiebox.stormsoul.client.geckolib.model.item;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.item.custom.geckolib.LabrasteelChargerItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class LabrasteelChargerItemModel extends GeoModel<LabrasteelChargerItem> {
    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("block/labrasteel_charger");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("textures/block/labrasteel_charger.png");
    }

    @Override
    public Identifier getAnimationResource(LabrasteelChargerItem item) {
        return Stormsoul.id("block/labrasteel_charger");
    }
}
