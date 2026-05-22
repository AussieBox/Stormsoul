package org.aussiebox.stormsoul.client.geckolib.renderer.item;

import org.aussiebox.stormsoul.client.geckolib.model.item.StormRodItemModel;
import org.aussiebox.stormsoul.item.custom.geckolib.StormRodItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class StormRodItemRenderer extends GeoItemRenderer<StormRodItem> {
    public StormRodItemRenderer() {
        super(new StormRodItemModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
