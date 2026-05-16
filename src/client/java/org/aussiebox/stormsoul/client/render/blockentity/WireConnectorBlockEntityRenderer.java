package org.aussiebox.stormsoul.client.render.blockentity;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.blockentity.custom.WireConnectorBlockEntity;
import org.aussiebox.stormsoul.util.WireType;
import org.joml.Matrix4f;

import java.util.Optional;

public class WireConnectorBlockEntityRenderer<T extends WireConnectorBlockEntity> implements BlockEntityRenderer<T> {

    public WireConnectorBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(T entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        if (entity.getWorld() == null) return;
        Optional<Vec3d> connectedOptional = entity.getConnectedTo();
        Optional<WireType> wireTypeOptional = entity.getWireType();
        if (connectedOptional.isEmpty() || wireTypeOptional.isEmpty()) return;
        Vec3d connectedTo = connectedOptional.get();
        WireType wireType = wireTypeOptional.get();

        if (entity.getEditor().isPresent())
            connectedTo = entity.getEditor().get().getLeashPos(tickProgress);

        double x1 = 0.5, y1 = 0.5, z1 = 0.5;
        double x2 = connectedTo.getX() - entity.getPos().getX() + 0.5;
        double y2 = connectedTo.getY() - entity.getPos().getY() + 0.5;
        double z2 = connectedTo.getZ() - entity.getPos().getZ() + 0.5;

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(Stormsoul.id("textures/entity/wire_connector/" + wireType.asString() + ".png")));
        Matrix4f posMatrix = matrices.peek().getPositionMatrix();

        double distance = Vec3d.of(entity.getPos()).distanceTo(connectedTo);
        int segments = (int) (distance*8);
        float thickness = wireType.getThickness();
        float sag = wireType.getSag();

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

            light = WorldRenderer.getLightmapCoordinates(entity.getWorld(), new BlockPos((int) (ax+ux-rx), (int) (ay+uy-ry), (int) (az+uz-rz)));

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

        VertexConsumer energyBuffer = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(Stormsoul.id("textures/entity/wire_connector/energy.png"), 1, 1));

        int i = 0;
        for (float progress : entity.getPowerProgresses()) {
            float lastProgress;
            if (progress == 0) lastProgress = 0;
            else lastProgress = entity.getLastPowerProgresses().get(i);

            float curCubeX = (float) MathHelper.lerp(progress, x1, x2);
            float curCubeY = (float) (MathHelper.lerp(progress, y1, y2) - (sag * 4 * progress * (1 - progress)));
            float curCubeZ = (float) MathHelper.lerp(progress, z1, z2);

            float lastCubeX = (float) MathHelper.lerp(lastProgress, x1, x2);
            float lastCubeY = (float) (MathHelper.lerp(lastProgress, y1, y2) - (sag * 4 * lastProgress * (1 - lastProgress)));
            float lastCubeZ = (float) MathHelper.lerp(lastProgress, z1, z2);

            float cubeX = MathHelper.lerp(tickProgress, lastCubeX, curCubeX);
            float cubeY = MathHelper.lerp(tickProgress, lastCubeY, curCubeY);
            float cubeZ = MathHelper.lerp(tickProgress, lastCubeZ, curCubeZ);

            float dirX = (float) (x2 - x1);
            float dirY = (float) ((y2 - y1) - (sag * 4.0f * (1.0f - 2.0f * progress)));
            float dirZ = (float) (z2 - z1);

            float len = MathHelper.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
            if (len > 0.0001f) {
                dirX /= len;
                dirY /= len;
                dirZ /= len;
            } else dirZ = 1.0f;

            float upX = 0.0f; float upY = 1.0f; float upZ = 0.0f;
            if (Math.abs(dirY) > 0.99f) {
                upY = 0.0f; upZ = 1.0f;
            }

            int cubeLight = 15728880;

            Matrix4f rotationMatrix = new Matrix4f().rotationTowards(-dirX, -dirY, -dirZ, upX, upY, upZ);
            matrices.translate(cubeX, cubeY, cubeZ);
            matrices.multiplyPositionMatrix(rotationMatrix);
            Matrix4f cubeMatrix = matrices.peek().getPositionMatrix();

            float s = thickness + 0.001f;

            /// Up Face
            drawQuad(energyBuffer, cubeMatrix, -s,  s, -s, -s,  s,  s,  s,  s,  s,  s,  s, -s,  0,  1,  0, cubeLight, overlay, 0, 1);
            /// Down Face
            drawQuad(energyBuffer, cubeMatrix, -s, -s,  s, -s, -s, -s,  s, -s, -s,  s, -s,  s,  0, -1,  0, cubeLight, overlay, 0, 1);
            /// North Face
            drawQuad(energyBuffer, cubeMatrix, -s,  s, -s,  s,  s, -s,  s, -s, -s, -s, -s, -s,  0,  0, -1, cubeLight, overlay, 0, 1);
            /// South Face
            drawQuad(energyBuffer, cubeMatrix,  s,  s,  s, -s,  s,  s, -s, -s,  s,  s, -s,  s,  0,  0,  1, cubeLight, overlay, 0, 1);
            /// West Face
            drawQuad(energyBuffer, cubeMatrix, -s,  s,  s, -s,  s, -s, -s, -s, -s, -s, -s,  s, -1,  0,  0, cubeLight, overlay, 0, 1);
            /// East Face
            drawQuad(energyBuffer, cubeMatrix,  s,  s, -s,  s,  s,  s,  s, -s,  s,  s, -s, -s,  1,  0,  0, cubeLight, overlay, 0, 1);

            i++;
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
