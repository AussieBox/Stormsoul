package org.aussiebox.stormsoul.client.geckolib.renderer;

import org.aussiebox.stormsoul.client.geckolib.model.SoulweaverNetModel;
import org.aussiebox.stormsoul.item.custom.SoulweaverNetItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SoulweaverNetRenderer extends GeoItemRenderer<SoulweaverNetItem> {
    public SoulweaverNetRenderer() {
        super(new SoulweaverNetModel());
    }
}
