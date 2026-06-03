package org.aussiebox.stormsoul.cca;

import lombok.Getter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.particle.type.PlayerAnchorParticleType;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PlayerComponent implements AutoSyncedComponent, ServerTickingComponent {
    public static final ComponentKey<PlayerComponent> KEY = ComponentRegistry.getOrCreate(Stormsoul.id("player"), PlayerComponent.class);
    private final PlayerEntity player;

    @Getter private int illuminosSurgeTicks = 0;
    @Getter private Vec3d lastParticlePos = Vec3d.ZERO;
    @Getter private double travelRemainder = 0.0;

    public PlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    public void startSurge() {
        if (player.getWorld() == null) return;

        illuminosSurgeTicks = 120;
        lastParticlePos = Vec3d.ZERO;

        player.getWorld().playSoundFromEntity(null, player, SoundEvent.of(Stormsoul.id("item.stormsoul_illuminos.surge_start")), SoundCategory.PLAYERS, 1, 1);
        player.getWorld().playSoundFromEntity(null, player, SoundEvent.of(Stormsoul.id("item.stormsoul_illuminos.surge")), SoundCategory.PLAYERS, 0.5F, 1);

        sync();
    }

    @Override
    public void serverTick() {
        World world = player.getWorld();
        if (!(world instanceof ServerWorld serverWorld)) return;

        if (illuminosSurgeTicks > 0) {
            illuminosSurgeTicks--;
            Vec3d center = player.getBoundingBox().getCenter();
            Vec3d movement = center.subtract(lastParticlePos);
            double distance = movement.length();

            double spacing = 1.75;

            if (distance > 0.0) {
                Vec3d direction = movement.normalize();

                double totalDistance = distance + travelRemainder;

                double currentDist = spacing - travelRemainder;

                while (currentDist <= distance) {
                    Vec3d spawnPos = lastParticlePos.add(direction.multiply(currentDist));

                    serverWorld.spawnParticles(
                            new PlayerAnchorParticleType(Stormsoul.SURGE_TRAIL, player.getUuid()),
                            spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(),
                            0, 0, 0, 0, 0
                    );

                    currentDist += spacing;
                }

                travelRemainder = totalDistance % spacing;
                lastParticlePos = center;
            }
        }

        sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void readData(ReadView tag) {
        illuminosSurgeTicks = tag.getInt("illuminosSurgeTicks", 0);
        lastParticlePos = tag.read("lastParticlePos", Vec3d.CODEC).orElse(Vec3d.ZERO);
        travelRemainder = tag.getDouble("travelRemainder", 0);
    }

    @Override
    public void writeData(WriteView tag) {
        tag.putInt("illuminosSurgeTicks", illuminosSurgeTicks);
        tag.put("lastParticlePos", Vec3d.CODEC, lastParticlePos);
        tag.putDouble("travelRemainder", travelRemainder);
    }
}
