package org.aussiebox.stormsoul.client.geckolib.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.aussiebox.stormsoul.blockentity.custom.ConnectedCasingBlockEntity;
import org.aussiebox.stormsoul.client.geckolib.model.ConnectedCasingModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ConnectedCasingRenderer extends GeoBlockRenderer<ConnectedCasingBlockEntity> {
    public ConnectedCasingRenderer() {
        super(new ConnectedCasingModel());
    }

    @Override
    public void render(ConnectedCasingBlockEntity animatable, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay, Vec3d cameraPosition) {
        if (model instanceof ConnectedCasingModel casingModel) casingModel.setTexture(animatable.getTexture());
        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay, cameraPosition);
    }
}
