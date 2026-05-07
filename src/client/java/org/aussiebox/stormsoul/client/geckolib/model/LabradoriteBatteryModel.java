package org.aussiebox.stormsoul.client.geckolib.model;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.custom.LabradoriteBatteryBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class LabradoriteBatteryModel extends GeoModel<LabradoriteBatteryBlockEntity> {
    private final Identifier texture;

    public LabradoriteBatteryModel(Identifier texture) {
        this.texture = texture;
    }

    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("block/labradorite_battery");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return texture;
    }

    @Override
    public Identifier getAnimationResource(LabradoriteBatteryBlockEntity labradoriteBatteryBlockEntity) {
        return Stormsoul.id("block/labradorite_battery");
    }
}
