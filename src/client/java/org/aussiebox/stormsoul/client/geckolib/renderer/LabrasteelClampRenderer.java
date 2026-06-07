package org.aussiebox.stormsoul.client.geckolib.renderer;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.aussiebox.stormsoul.block.custom.LabrasteelClampBlock;
import org.aussiebox.stormsoul.blockentity.custom.LabrasteelClampBlockEntity;
import org.aussiebox.stormsoul.client.geckolib.model.LabrasteelClampModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class LabrasteelClampRenderer extends GeoBlockRenderer<LabrasteelClampBlockEntity> {
    public LabrasteelClampRenderer() {
        super(new LabrasteelClampModel());
    }

    @Override
    public void render(LabrasteelClampBlockEntity animatable, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay, Vec3d cameraPosition) {
        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay, cameraPosition);
        if (animatable.getWorld() == null) return;

        poseStack.translate(0.5f, 0f, 0.5f);
        BlockState state = animatable.getWorld().getBlockState(animatable.getPos());
        switch (state.get(LabrasteelClampBlock.FACING, Direction.UP)) {
            case UP -> poseStack.translate(0f, 0.8f, 0f);
            case DOWN -> {
                poseStack.translate(0f, 0.2f, 0f);
                poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
            }
            case NORTH -> {
                poseStack.translate(0f, 0.5f, -0.3f);
                poseStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));
            }
            case SOUTH -> {
                poseStack.translate(0f, 0.5f, 0.3f);
                poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
            }
            case EAST -> {
                poseStack.translate(0.3f, 0.5f, 0f);
                poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                poseStack.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
            }
            case WEST -> {
                poseStack.translate(-0.3f, 0.5f, 0f);
                poseStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));
                poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
            }
            case null, default -> {}
        }
        MinecraftClient.getInstance().getItemRenderer().renderItem(animatable.getStack(), ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, bufferSource, animatable.getWorld(), 0);
    }

    @Override
    protected void rotateBlock(Direction facing, MatrixStack poseStack) {
        switch (facing) {
            case DOWN -> {
                poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
                poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                poseStack.translate(0, -1, 0);
            }
            case EAST -> {
                poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90));
                poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                poseStack.translate(0, -0.5, -0.5);
            }
            case WEST -> {
                poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
                poseStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(90));
                poseStack.translate(0, -0.5, -0.5);
            }
            case NORTH -> {
                poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
                poseStack.translate(0, -0.5, 0.5);
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
