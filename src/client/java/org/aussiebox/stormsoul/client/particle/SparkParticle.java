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

import java.util.Random;

public class SparkParticle extends AnimatedParticle {
    public static final Random sparkRandom = new Random();
    private final boolean fallLeft;

    protected SparkParticle(ClientWorld clientWorld, double x, double y, double z, SpriteProvider sprites) {
        super(clientWorld, x, y, z, sprites, 0.0125F);
        this.velocityMultiplier = 0.9F;
        this.maxAge = 20 + random.nextBetween(0, 20);
        this.lastAngle = sparkRandom.nextFloat(-0.225F, 0.225F);
        this.angle = this.lastAngle;
        this.scale = sparkRandom.nextFloat(0.08F, 0.12F);
        this.setTargetColor(Colors.WHITE);
        fallLeft = random.nextBoolean();
        setSpriteForAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.lastAngle = this.angle;
        if (fallLeft) this.angle -= 0.01F;
        else this.angle += 0.01F;
        this.scale -= 0.0025F;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SparkParticle(clientWorld, d, e, f, this.sprites);
        }
    }
}
