package org.aussiebox.stormsoul.client.geckolib.renderer;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import org.aussiebox.stormsoul.blockentity.custom.LabrasteelChargerBlockEntity;
import org.aussiebox.stormsoul.client.geckolib.model.LabrasteelChargerModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class LabrasteelChargerRenderer extends GeoBlockRenderer<LabrasteelChargerBlockEntity> {
    public LabrasteelChargerRenderer() {
        super(new LabrasteelChargerModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    protected void rotateBlock(Direction facing, MatrixStack poseStack) {
        switch (facing) {
            case DOWN -> {
                poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
                poseStack.translate(0, -1, 0);
            }
            case EAST -> {
                poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90));
                poseStack.translate(-0.5, -0.5, 0);
            }
            case WEST -> {
                poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
                poseStack.translate(0.5, -0.5, 0);
            }
            case NORTH -> {
                poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
                poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                poseStack.translate(0, -0.5, -0.5);
            }
            case SOUTH -> {
                poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                poseStack.translate(0, -0.5, 0.5);
            }
            case null, default -> {}
        }
    }
}
