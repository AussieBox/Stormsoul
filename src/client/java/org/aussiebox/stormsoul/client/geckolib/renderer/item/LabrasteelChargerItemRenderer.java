package org.aussiebox.stormsoul.client.geckolib.renderer.item;

import org.aussiebox.stormsoul.client.geckolib.model.item.LabrasteelChargerItemModel;
import org.aussiebox.stormsoul.item.custom.geckolib.LabrasteelChargerItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class LabrasteelChargerItemRenderer extends GeoItemRenderer<LabrasteelChargerItem> {
    public LabrasteelChargerItemRenderer() {
        super(new LabrasteelChargerItemModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
