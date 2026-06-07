package org.aussiebox.stormsoul.client.geckolib.model.item;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.item.custom.geckolib.LabrasteelClampItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class LabrasteelClampItemModel extends GeoModel<LabrasteelClampItem> {
    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("block/labrasteel_clamp");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("textures/block/labrasteel_clamp.png");
    }

    @Override
    public Identifier getAnimationResource(LabrasteelClampItem item) {
        return Stormsoul.id("block/labrasteel_clamp");
    }
}
