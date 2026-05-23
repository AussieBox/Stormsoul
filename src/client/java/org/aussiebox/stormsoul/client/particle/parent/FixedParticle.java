package org.aussiebox.stormsoul.client.particle.parent;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public abstract class FixedParticle extends Particle {
    protected float scale = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;
    protected Sprite sprite;
    @Getter @Setter protected double pitch;
    @Getter @Setter protected double yaw;

    protected FixedParticle(ClientWorld clientWorld, double x, double y, double z) {
        super(clientWorld, x, y, z);
        this.pitch = 0;
        this.yaw = 0;
    }

    protected FixedParticle(ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ) {
        super(clientWorld, x, y, z, velX, velY, velZ);
        this.pitch = 0;
        this.yaw = 0;
    }

    protected FixedParticle(ClientWorld clientWorld, double x, double y, double z, double pitch, double yaw) {
        super(clientWorld, x, y, z);
        this.pitch = pitch;
        this.yaw = yaw;
    }

    protected FixedParticle(ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ, double pitch, double yaw) {
        super(clientWorld, x, y, z, velX, velY, velZ);
        this.pitch = pitch;
        this.yaw = yaw;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tickProgress) {
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotateY((float) Math.toRadians(-this.yaw));
        quaternionf.rotateZ((float) Math.toRadians((float) this.pitch));

        if (this.angle != 0.0F) {
            quaternionf.rotateZ(MathHelper.lerp(tickProgress, this.lastAngle, this.angle));
        }

        float x = (float) (MathHelper.lerp(tickProgress, this.lastX, this.x) - camera.getPos().getX());
        float y = (float) (MathHelper.lerp(tickProgress, this.lastY, this.y) - camera.getPos().getY());
        float z = (float) (MathHelper.lerp(tickProgress, this.lastZ, this.z) - camera.getPos().getZ());

        this.render(vertexConsumer, quaternionf, x, y, z, tickProgress);
    }

    protected void render(VertexConsumer vertexConsumer, Quaternionf quaternionf, float x, float y, float z, float tickProgress) {
        float f = this.getSize(tickProgress);
        float g = this.getMinU();
        float h = this.getMaxU();
        float i = this.getMinV();
        float j = this.getMaxV();
        int k = this.getBrightness(tickProgress);
        this.renderVertex(vertexConsumer, quaternionf, x, y, z, 1.0F, -1.0F, f, h, j, k);
        this.renderVertex(vertexConsumer, quaternionf, x, y, z, 1.0F, 1.0F, f, h, i, k);
        this.renderVertex(vertexConsumer, quaternionf, x, y, z, -1.0F, 1.0F, f, g, i, k);
        this.renderVertex(vertexConsumer, quaternionf, x, y, z, -1.0F, -1.0F, f, g, j, k);

        this.renderVertex(vertexConsumer, quaternionf, x, y, z, -1.0F, -1.0F, f, g, j, k);
        this.renderVertex(vertexConsumer, quaternionf, x, y, z, -1.0F, 1.0F, f, g, i, k);
        this.renderVertex(vertexConsumer, quaternionf, x, y, z, 1.0F, 1.0F, f, h, i, k);
        this.renderVertex(vertexConsumer, quaternionf, x, y, z, 1.0F, -1.0F, f, h, j, k);
    }

    private void renderVertex(
            VertexConsumer vertexConsumer, Quaternionf quaternionf, float x, float y, float z, float f, float g, float size, float u, float v, int light
    ) {
        Vector3f vector3f = new Vector3f(f, g, 0.0F).rotate(quaternionf).mul(size).add(x, y, z);
        vertexConsumer.vertex(vector3f.x(), vector3f.y(), vector3f.z()).texture(u, v).color(this.red, this.green, this.blue, this.alpha).light(light);
    }

    /**
     * {@return the draw scale of this particle, which is used while rendering in {@link #buildGeometry}}
     */
    public float getSize(float tickProgress) {
        return this.scale;
    }

    @Override
    public Particle scale(float scale) {
        this.scale *= scale;
        return super.scale(scale);
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    protected float getMinU() {
        return this.sprite.getMinU();
    }

    protected float getMaxU() {
        return this.sprite.getMaxU();
    }

    protected float getMinV() {
        return this.sprite.getMinV();
    }

    protected float getMaxV() {
        return this.sprite.getMaxV();
    }

    /**
     * Sets the current {@link Sprite} of this particle to a random frame in its atlas sheet.
     *
     * @param spriteProvider sprite access for retrieving random {@link Sprite} frames
     */
    public void setSprite(SpriteProvider spriteProvider) {
        this.setSprite(spriteProvider.getSprite(this.random));
    }

    /**
     * Sets the current {@link Sprite} of this particle based on the age of the particle, assuming the particle texture is an atlas with multiple frames.
     *
     * @param spriteProvider sprite access for retrieving the proper {@link Sprite} based on lifetime progress
     */
    public void setSpriteForAge(SpriteProvider spriteProvider) {
        if (!this.dead) {
            this.setSprite(spriteProvider.getSprite(this.age, this.maxAge));
        }
    }
}
