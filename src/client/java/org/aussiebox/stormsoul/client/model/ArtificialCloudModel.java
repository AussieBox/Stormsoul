package org.aussiebox.stormsoul.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.math.MathHelper;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.custom.ArtificialCloudBlockEntity;

public class ArtificialCloudModel extends Model {
    public static final EntityModelLayer LAYER = new EntityModelLayer(Stormsoul.id("artificial_cloud"), "main");
    private final ModelPart mainCloud;
    private final ModelPart sideCloud1;
    private final ModelPart sideCloud2;
    private final ModelPart sideCloud3;
    private final ModelPart sideCloud4;

    public ArtificialCloudModel(ModelPart root) {
        super(root, RenderLayer::getEntityTranslucent);
        mainCloud = root.getChild("main_cloud");
        sideCloud1 = root.getChild("side_cloud_1");
        sideCloud2 = root.getChild("side_cloud_2");
        sideCloud3 = root.getChild("side_cloud_3");
        sideCloud4 = root.getChild("side_cloud_4");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData rootData = modelData.getRoot();
        ModelPartData mainCloud = rootData.addChild("main_cloud", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -9.0F, -6.0F, 12.0F, 6.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(8.0F, 9.0F, 8.0F));
        ModelPartData sideCloud1 = rootData.addChild("side_cloud_1", ModelPartBuilder.create().uv(24, 18).cuboid(2.0F, -7.0F, -8.0F, 6.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(8.0F, 9.0F, 8.0F));
        ModelPartData sideCloud2 = rootData.addChild("side_cloud_2", ModelPartBuilder.create().uv(24, 28).cuboid(1.0F, -10.0F, 3.0F, 6.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(8.0F, 9.0F, 8.0F));
        ModelPartData sideCloud3 = rootData.addChild("side_cloud_3", ModelPartBuilder.create().uv(0, 18).cuboid(-8.0F, -8.0F, 1.0F, 5.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(8.0F, 9.0F, 8.0F));
        ModelPartData sideCloud4 = rootData.addChild("side_cloud_4", ModelPartBuilder.create().uv(0, 29).cuboid(-8.0F, -8.0F, -7.0F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(8.0F, 9.0F, 8.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void update(ArtificialCloudBlockEntity blockEntity, float tickProgress) {
        this.root.originY = MathHelper.lerp(tickProgress, blockEntity.lastLargeCloudOffset, blockEntity.largeCloudOffset);
    }
}
