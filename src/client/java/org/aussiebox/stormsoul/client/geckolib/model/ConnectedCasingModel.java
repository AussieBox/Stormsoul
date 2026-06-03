package org.aussiebox.stormsoul.client.geckolib.model;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.custom.ConnectedCasingBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ConnectedCasingModel extends GeoModel<ConnectedCasingBlockEntity> {
    @Getter @Setter private Identifier texture;

    public ConnectedCasingModel() {
        this.texture = Stormsoul.id("textures/block/silver_casing.png");
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
    public Identifier getAnimationResource(ConnectedCasingBlockEntity blockEntity) {
        return Stormsoul.id("block/connected_casing");
    }
}
