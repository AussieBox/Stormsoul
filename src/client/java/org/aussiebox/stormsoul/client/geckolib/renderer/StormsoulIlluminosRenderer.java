package org.aussiebox.stormsoul.client.geckolib.renderer;

import org.aussiebox.stormsoul.client.geckolib.model.StormsoulIlluminosModel;
import org.aussiebox.stormsoul.item.custom.StormsoulIlluminosItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class StormsoulIlluminosRenderer extends GeoItemRenderer<StormsoulIlluminosItem> {
    public StormsoulIlluminosRenderer() {
        super(new StormsoulIlluminosModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
