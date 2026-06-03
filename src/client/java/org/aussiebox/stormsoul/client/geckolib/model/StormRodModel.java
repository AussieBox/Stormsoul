package org.aussiebox.stormsoul.client.geckolib.model;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.custom.StormRodBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class StormRodModel extends GeoModel<StormRodBlockEntity> {
    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("block/storm_rod");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("textures/block/storm_rod.png");
    }

    @Override
    public Identifier getAnimationResource(StormRodBlockEntity blockEntity) {
        return Stormsoul.id("block/storm_rod");
    }
}
