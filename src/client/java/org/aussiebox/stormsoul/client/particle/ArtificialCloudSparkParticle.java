package org.aussiebox.stormsoul.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.Colors;
import org.jetbrains.annotations.NotNull;

public class ArtificialCloudSparkParticle extends AnimatedParticle {
    private final SpriteProvider sprites;

    protected ArtificialCloudSparkParticle(ClientWorld clientWorld, double x, double y, double z, SpriteProvider sprites) {
        super(clientWorld, x, y, z, sprites, 0.0125F);
        this.sprites = sprites;
        this.velocityMultiplier = 0.9F;
        this.maxAge = 20 + random.nextBetween(0, 20);
        this.angle = random.nextBetween(-45, 45);
        this.setTargetColor(Colors.WHITE);
        this.setSprite(spriteProvider.getSprite(random));
    }

    @Override
    public void tick() {
        if (this.age++ >= this.maxAge)
            this.markDead();
        this.angle += 0.01F;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ArtificialCloudSparkParticle(clientWorld, d, e, f, this.sprites);
        }
    }
}
