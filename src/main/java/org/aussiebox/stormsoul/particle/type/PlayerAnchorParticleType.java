package org.aussiebox.stormsoul.particle.type;

import com.mojang.serialization.MapCodec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Uuids;
import net.minecraft.world.World;

import java.util.UUID;

public class PlayerAnchorParticleType implements ParticleEffect {
    private final ParticleType<PlayerAnchorParticleType> type;
    private final UUID uuid;

    public PlayerAnchorParticleType(ParticleType<PlayerAnchorParticleType> type, UUID uuid) {
        this.type = type;
        this.uuid = uuid;
    }

    public static MapCodec<PlayerAnchorParticleType> createCodec(ParticleType<PlayerAnchorParticleType> type) {
        return Uuids.CODEC.xmap(uuid -> new PlayerAnchorParticleType(type, uuid), effect -> effect.uuid)
                .fieldOf("uuid");
    }

    public static PacketCodec<? super RegistryByteBuf, PlayerAnchorParticleType> createPacketCodec(ParticleType<PlayerAnchorParticleType> type) {
        return Uuids.PACKET_CODEC.xmap(uuid -> new PlayerAnchorParticleType(type, uuid), effect -> effect.uuid);
    }

    public PlayerEntity getPlayer(World world) {
        return world.getPlayerByUuid(uuid);
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }
}

