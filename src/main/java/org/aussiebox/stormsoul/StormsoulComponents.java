package org.aussiebox.stormsoul;

import org.aussiebox.stormsoul.cca.ArtificialStormComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class StormsoulComponents implements EntityComponentInitializer, WorldComponentInitializer {

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
//        registry.beginRegistration(PlayerEntity.class, ShimmerComponent.KEY)
//                .respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY)
//                .end(ShimmerComponent::new);
    }

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(ArtificialStormComponent.KEY, ArtificialStormComponent::new);
    }
}
