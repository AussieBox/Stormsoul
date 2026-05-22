package org.aussiebox.stormsoul.client.geckolib.model;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.custom.LabrasteelBatteryBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class LabrasteelBatteryModel extends GeoModel<LabrasteelBatteryBlockEntity> {
    @Getter @Setter private Identifier texture;

    public LabrasteelBatteryModel() {
        this.texture = Stormsoul.id("textures/block/copper_labrasteel_battery.png");
    }

    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("block/labrasteel_battery");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return texture;
    }

    @Override
    public Identifier getAnimationResource(LabrasteelBatteryBlockEntity labradoriteBatteryBlockEntity) {
        return Stormsoul.id("block/labrasteel_battery");
    }
}
