package org.aussiebox.stormsoul.client.geckolib.model;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.item.custom.StormsoulIlluminosItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class StormsoulIlluminosModel extends GeoModel<StormsoulIlluminosItem> {
    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("item/stormsoul_illuminos");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("textures/item/stormsoul_illuminos.png");
    }

    @Override
    public Identifier getAnimationResource(StormsoulIlluminosItem blockEntity) {
        return Stormsoul.id("item/stormsoul_illuminos");
    }
}
