package org.aussiebox.stormsoul.client.render.blockentity;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.custom.ArtificialCloudBlockEntity;
import org.aussiebox.stormsoul.client.model.ArtificialCloudModel;

public class ArtificialCloudBlockEntityRenderer implements BlockEntityRenderer<ArtificialCloudBlockEntity> {
    public static final Identifier CLOUD_TEXTURE = Stormsoul.id("textures/entity/artificial_cloud/cloud.png");
    private final ArtificialCloudModel cloudModel;

    public ArtificialCloudBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.cloudModel = new ArtificialCloudModel(context.getLayerModelPart(ArtificialCloudModel.LAYER));
    }

    @Override
    public void render(ArtificialCloudBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        matrices.push();

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(CLOUD_TEXTURE));
        this.cloudModel.update(entity, tickProgress);
        this.cloudModel.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }

    @Override
    public boolean rendersOutsideBoundingBox() {
        return true;
    }
}
