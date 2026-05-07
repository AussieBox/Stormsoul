package org.aussiebox.stormsoul.client.geckolib.renderer;

import org.aussiebox.stormsoul.blockentity.custom.LabradoriteBatteryBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class LabradoriteBatteryRenderer extends GeoBlockRenderer<LabradoriteBatteryBlockEntity> {
    public LabradoriteBatteryRenderer(GeoModel<LabradoriteBatteryBlockEntity> model) {
        super(model);
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
