package org.aussiebox.stormsoul;

import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class StormsoulComponents implements EntityComponentInitializer {

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
//        registry.beginRegistration(PlayerEntity.class, ShimmerComponent.KEY)
//                .respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY)
//                .end(ShimmerComponent::new);
    }

}
