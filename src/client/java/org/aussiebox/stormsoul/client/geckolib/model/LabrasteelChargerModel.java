package org.aussiebox.stormsoul.client.geckolib.model;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.custom.LabrasteelChargerBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class LabrasteelChargerModel extends GeoModel<LabrasteelChargerBlockEntity> {
    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("block/labrasteel_charger");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("textures/block/labrasteel_charger.png");
    }

    @Override
    public Identifier getAnimationResource(LabrasteelChargerBlockEntity blockEntity) {
        return Stormsoul.id("block/labrasteel_charger");
    }
}
