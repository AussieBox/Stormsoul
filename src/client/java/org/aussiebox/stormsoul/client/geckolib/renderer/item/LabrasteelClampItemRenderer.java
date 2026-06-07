package org.aussiebox.stormsoul.client.geckolib.renderer.item;

import org.aussiebox.stormsoul.client.geckolib.model.item.LabrasteelClampItemModel;
import org.aussiebox.stormsoul.item.custom.geckolib.LabrasteelClampItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class LabrasteelClampItemRenderer extends GeoItemRenderer<LabrasteelClampItem> {
    public LabrasteelClampItemRenderer() {
        super(new LabrasteelClampItemModel());
    }
}
