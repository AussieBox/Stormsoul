package org.aussiebox.stormsoul.client.geckolib.model.item;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.item.custom.geckolib.ConnectedCasingItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ConnectedCasingItemModel extends GeoModel<ConnectedCasingItem> {
    private final Identifier texture;

    public ConnectedCasingItemModel(Identifier texture) {
        this.texture = texture;
    }

    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("block/connected_casing");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return texture;
    }

    @Override
    public Identifier getAnimationResource(ConnectedCasingItem item) {
        return Stormsoul.id("block/connected_casing");
    }
}
