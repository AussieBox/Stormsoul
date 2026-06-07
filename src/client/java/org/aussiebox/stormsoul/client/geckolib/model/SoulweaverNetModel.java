package org.aussiebox.stormsoul.client.geckolib.model;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.item.custom.SoulweaverNetItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class SoulweaverNetModel extends GeoModel<SoulweaverNetItem> {
    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("item/soulweaver_net");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return Stormsoul.id("textures/item/soulweaver_net.png");
    }

    @Override
    public Identifier getAnimationResource(SoulweaverNetItem item) {
        return Stormsoul.id("item/soulweaver_net");
    }
}
