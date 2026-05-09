package org.aussiebox.stormsoul.client.render.blockentity;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.custom.WireConnectorBlockEntity;
import org.joml.Matrix4f;

import java.util.Optional;

public class WireConnectorBlockEntityRenderer<T extends WireConnectorBlockEntity> implements BlockEntityRenderer<T> {
    private static final float HANG = 0.5F;
    private static final float MAX_LENGTH = 16F;

    public WireConnectorBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(T entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        Optional<Vec3d> connectedOptional = entity.getConnectedTo();
        if (connectedOptional.isEmpty()) return;
        Vec3d connectedTo = connectedOptional.get();

        if (entity.getEditor().isPresent())
            connectedTo = entity.getEditor().get().getLeashPos(tickProgress);

        double x1 = 0.5, y1 = 0.5, z1 = 0.5;
        double x2 = connectedTo.getX() - entity.getPos().getX() + 0.5;
        double y2 = connectedTo.getY() - entity.getPos().getY() + 0.5;
        double z2 = connectedTo.getZ() - entity.getPos().getZ() + 0.5;

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(Stormsoul.id("textures/entity/wire_connector/copper.png")));
        Matrix4f posMatrix = matrices.peek().getPositionMatrix();

        double distance = Vec3d.of(entity.getPos()).distanceTo(connectedTo);
        int segments = (int) (distance*8);
        float thickness = 0.05f;
        float sag = 0.8f;

        for (int i = 0; i < segments; i++) {
            float f1 = (float) i / segments;
            float f2 = (float) (i + 1) / segments;

            float f2_overlap = f2 + (1.0f / segments) * 0.05f;

            float ax = (float) MathHelper.lerp(f1, x1, x2);
            float ay = (float) (MathHelper.lerp(f1, y1, y2) - (sag * 4 * f1 * (1 - f1)));
            float az = (float) MathHelper.lerp(f1, z1, z2);

            float bx = (float) MathHelper.lerp(f2_overlap, x1, x2);
            float by = (float) (MathHelper.lerp(f2_overlap, y1, y2) - (sag * 4 * f2_overlap * (1 - f2_overlap)));
            float bz = (float) MathHelper.lerp(f2_overlap, z1, z2);

            float textureScale = 1.0f;
            float u1 = (float) (f1 * distance * textureScale);
            float u2 = (float) (f2_overlap * distance * textureScale);

            float dx = bx - ax;
            float dy = by - ay;
            float dz = bz - az;
            float mag = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= mag; dy /= mag; dz /= mag;

            float rx = dz * thickness;
            float ry = 0;
            float rz = -dx * thickness;
            float ux = dy * rz - dz * ry;
            float uy = dz * rx - dx * rz;
            float uz = dx * ry - dy * rx;

            /// Top face
            drawQuad(buffer, posMatrix,
                    ax+ux-rx, ay+uy-ry, az+uz-rz,
                    bx+ux-rx, by+uy-ry, bz+uz-rz,
                    bx+ux+rx, by+uy+ry, bz+uz+rz,
                    ax+ux+rx, ay+uy+ry, az+uz+rz,
                    ux, uy, uz, light, overlay, u1, u2);

            /// Bottom face
            drawQuad(buffer, posMatrix,
                    ax-ux+rx, ay-uy+ry, az-uz+rz,
                    bx-ux+rx, by-uy+ry, bz-uz+rz,
                    bx-ux-rx, by-uy-ry, bz-uz-rz,
                    ax-ux-rx, ay-uy-ry, az-uz-rz,
                    -ux, -uy, -uz, light, overlay, u1, u2);

            /// Left face
            drawQuad(buffer, posMatrix,
                    ax-ux-rx, ay-uy-ry, az-uz-rz,
                    bx-ux-rx, by-uy-ry, bz-uz-rz,
                    bx+ux-rx, by+uy-ry, bz+uz-rz,
                    ax+ux-rx, ay+uy-ry, az+uz-rz,
                    -rx, -ry, -rz, light, overlay, u1, u2);

            /// Right face
            drawQuad(buffer, posMatrix,
                    ax+ux+rx, ay+uy+ry, az+uz+rz,
                    bx+ux+rx, by+uy+ry, bz+uz+rz,
                    bx-ux+rx, by-uy+ry, bz-uz+rz,
                    ax-ux+rx, ay-uy+ry, az-uz+rz,
                    rx, ry, rz, light, overlay, u1, u2);
        }
    }

    private void drawQuad(VertexConsumer buffer, Matrix4f matrix,
                          float x1, float y1, float z1,
                          float x2, float y2, float z2,
                          float x3, float y3, float z3,
                          float x4, float y4, float z4,
                          float nx, float ny, float nz, int light, int overlay, float u1, float u2) {

        buffer.vertex(matrix, x1, y1, z1).color(1f, 1f, 1f, 1f).texture(u1, 0f).overlay(overlay).light(light).normal(nx, ny, nz);
        buffer.vertex(matrix, x2, y2, z2).color(1f, 1f, 1f, 1f).texture(u2, 0f).overlay(overlay).light(light).normal(nx, ny, nz);
        buffer.vertex(matrix, x3, y3, z3).color(1f, 1f, 1f, 1f).texture(u2, 1f).overlay(overlay).light(light).normal(nx, ny, nz);
        buffer.vertex(matrix, x4, y4, z4).color(1f, 1f, 1f, 1f).texture(u1, 1f).overlay(overlay).light(light).normal(nx, ny, nz);
    }

    @Override
    public boolean rendersOutsideBoundingBox() {
        return true;
    }
}
