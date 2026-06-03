package org.aussiebox.stormsoul.client.geckolib.renderer.item;

import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.client.geckolib.model.item.ConnectedCasingItemModel;
import org.aussiebox.stormsoul.item.custom.geckolib.ConnectedCasingItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ConnectedCasingItemRenderer extends GeoItemRenderer<ConnectedCasingItem> {
    public ConnectedCasingItemRenderer(Identifier texture) {
        super(new ConnectedCasingItemModel(texture));
    }
}
