package org.aussiebox.stormsoul.client.particle.parent;

import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;

public class FixedAnimatedParticle extends FixedParticle {
    protected final SpriteProvider spriteProvider;
    protected float targetRed;
    protected float targetGreen;
    protected float targetBlue;
    protected boolean changesColor;

    protected FixedAnimatedParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration) {
        super(world, x, y, z);
        this.velocityMultiplier = 0.91F;
        this.gravityStrength = upwardsAcceleration;
        this.spriteProvider = spriteProvider;
    }

    protected FixedAnimatedParticle(ClientWorld world, double x, double y, double z, double pitch, double yaw, SpriteProvider spriteProvider, float upwardsAcceleration) {
        super(world, x, y, z, pitch, yaw);
        this.velocityMultiplier = 0.91F;
        this.gravityStrength = upwardsAcceleration;
        this.spriteProvider = spriteProvider;
    }

    public void setColor(int rgbHex) {
        float f = ((rgbHex & 0xFF0000) >> 16) / 255.0F;
        float g = ((rgbHex & 0xFF00) >> 8) / 255.0F;
        float h = ((rgbHex & 0xFF)) / 255.0F;
        float i = 1.0F;
        this.setColor(f, g, h);
    }

    public void setTargetColor(int rgbHex) {
        this.targetRed = ((rgbHex & 0xFF0000) >> 16) / 255.0F;
        this.targetGreen = ((rgbHex & 0xFF00) >> 8) / 255.0F;
        this.targetBlue = ((rgbHex & 0xFF)) / 255.0F;
        this.changesColor = true;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
        if (this.age > this.maxAge / 2) {
            this.setAlpha(1.0F - ((float)this.age - (float) this.maxAge / 2) / this.maxAge);
            if (this.changesColor) {
                this.red = this.red + (this.targetRed - this.red) * 0.2F;
                this.green = this.green + (this.targetGreen - this.green) * 0.2F;
                this.blue = this.blue + (this.targetBlue - this.blue) * 0.2F;
            }
        }
    }

    @Override
    public int getBrightness(float tint) {
        return 15728880;
    }
}
