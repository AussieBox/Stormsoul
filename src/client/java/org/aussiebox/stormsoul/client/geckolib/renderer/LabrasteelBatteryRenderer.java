package org.aussiebox.stormsoul.client.geckolib.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.aussiebox.stormsoul.blockentity.custom.LabrasteelBatteryBlockEntity;
import org.aussiebox.stormsoul.client.geckolib.model.LabrasteelBatteryModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class LabrasteelBatteryRenderer extends GeoBlockRenderer<LabrasteelBatteryBlockEntity> {
    public LabrasteelBatteryRenderer() {
        super(new LabrasteelBatteryModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void render(LabrasteelBatteryBlockEntity animatable, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay, Vec3d cameraPosition) {
        if (model instanceof LabrasteelBatteryModel batteryModel) batteryModel.setTexture(animatable.getTexture());
        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay, cameraPosition);
    }
}
