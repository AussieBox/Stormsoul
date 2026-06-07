package org.aussiebox.stormsoul.client.geckolib.model;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.custom.LabrasteelClampBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class LabrasteelClampModel extends GeoModel<LabrasteelClampBlockEntity> {
    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("block/labrasteel_clamp");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("textures/block/labrasteel_clamp.png");
    }

    @Override
    public Identifier getAnimationResource(LabrasteelClampBlockEntity blockEntity) {
        return Stormsoul.id("block/labrasteel_clamp");
    }
}
