package org.aussiebox.stormsoul.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Colors;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.aussiebox.stormsoul.cca.PlayerComponent;
import org.aussiebox.stormsoul.client.particle.parent.FixedAnimatedParticle;
import org.aussiebox.stormsoul.particle.type.PlayerAnchorParticleType;
import org.aussiebox.stormsoul.util.StormsoulUtil;
import org.jetbrains.annotations.NotNull;

public class SurgeTrailParticle extends FixedAnimatedParticle {
    protected SurgeTrailParticle(ClientWorld world, double x, double y, double z, PlayerEntity anchor, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0125F);
        this.setMaxAge(120);
        this.setTargetColor(Colors.WHITE);
        this.scale = 1.0F;
        this.collidesWithWorld = false;
        this.gravityStrength = 0.0F;
        setSpriteForAge(spriteProvider);

        if (anchor == null) return;
        PlayerComponent component = PlayerComponent.KEY.get(anchor);

        Vec2f angles = StormsoulUtil.faceVecToVec(new Vec3d(x, y, z), component.getLastParticlePos());
        setPitch(angles.x);
        setYaw(angles.y-90);
    }

    @Override
    public void tick() {
        long gameTime = this.world.getTime();
        int currentFrame = (int) (gameTime % 28);
        this.setSprite(this.spriteProvider.getSprite(currentFrame, 28));
        if (this.age++ > this.maxAge) this.markDead();
        if (this.age > this.maxAge / 2) {
            this.setAlpha(1.0F - ((float)this.age - (float) this.maxAge / 2) / this.maxAge);
            if (this.changesColor) {
                this.red = this.red + (this.targetRed - this.red) * 0.2F;
                this.green = this.green + (this.targetGreen - this.green) * 0.2F;
                this.blue = this.blue + (this.targetBlue - this.blue) * 0.2F;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<PlayerAnchorParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(@NotNull PlayerAnchorParticleType particleType, @NotNull ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SurgeTrailParticle(clientWorld, d, e, f, particleType.getPlayer(clientWorld), this.sprites);
        }
    }
}
