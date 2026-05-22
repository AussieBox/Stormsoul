package org.aussiebox.stormsoul.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.Colors;
import org.aussiebox.stormsoul.client.particle.parent.FixedAnimatedParticle;
import org.jetbrains.annotations.NotNull;

public class SurgeTrailParticle extends FixedAnimatedParticle {
    protected SurgeTrailParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0125F);
        this.setMaxAge(100);
        this.setTargetColor(Colors.WHITE);
        this.scale = 1.0F;
        this.collidesWithWorld = false;
        this.gravityStrength = 0.0F;
        setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SurgeTrailParticle(clientWorld, d, e, f, this.sprites);
        }
    }
}
