package org.aussiebox.stormsoul;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.blockentity.custom.ArtificialCloudBlockEntity;
import org.aussiebox.stormsoul.blockentity.custom.LabrasteelBatteryBlockEntity;
import org.aussiebox.stormsoul.blockentity.custom.LabrasteelChargerBlockEntity;
import org.aussiebox.stormsoul.command.MainCommand;
import org.aussiebox.stormsoul.component.ModDataComponentTypes;
import org.aussiebox.stormsoul.item.ModItems;
import org.aussiebox.stormsoul.particle.type.PlayerAnchorParticleType;
import org.aussiebox.stormsoul.recipe.ModRecipes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.loading.math.MolangQueries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stormsoul implements ModInitializer {

    public static final String MOD_ID = "stormsoul";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Random RANDOM = new Random();

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static final SimpleParticleType ARTIFICIAL_CLOUD_SPARK = FabricParticleTypes.simple();
    public static final SimpleParticleType STORMSOUL_SPARK = FabricParticleTypes.simple();
    public static final ParticleType<PlayerAnchorParticleType> SURGE_TRAIL = FabricParticleTypes.complex(PlayerAnchorParticleType::createCodec, PlayerAnchorParticleType::createPacketCodec);

    public static final RegistryKey<PlacedFeature> LABRADORITE_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, id("ore_labradorite"));
    public static final RegistryKey<PlacedFeature> LABRADORITE_ORE_BURIED_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, id("ore_labradorite_buried"));
    public static final RegistryKey<PlacedFeature> SILVER_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, id("ore_silver"));
    public static final RegistryKey<PlacedFeature> SILVER_ORE_BURIED_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, id("ore_silver_buried"));

    public static List<String> initMessages = new ArrayList<>();

    @Override
    public void onInitialize() {
        initMessages.add("Cooking up a storm...");
        initMessages.add("Preparing elec-teriffic technology...");
        initMessages.add("Who let the souls out?");
        initMessages.add("Did you know? Silver is the most conductive metal on the planet!");
        initMessages.add("Did you know? Labradorite is NOT the most conductive metal on the planet! This is because it is not a metal.");
        LOGGER.info(initMessages.get(RANDOM.nextInt(initMessages.size())));

        ModBlocks.init();
        ModBlockEntities.init();
        ModItems.init();
        ModDataComponentTypes.init();
        ModRecipes.init();

        Registry.register(Registries.PARTICLE_TYPE, id("artificial_cloud_spark"), ARTIFICIAL_CLOUD_SPARK);
        Registry.register(Registries.PARTICLE_TYPE, id("stormsoul_spark"), STORMSOUL_SPARK);
        Registry.register(Registries.PARTICLE_TYPE, id("surge_trail"), SURGE_TRAIL);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, LABRADORITE_ORE_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, LABRADORITE_ORE_BURIED_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, SILVER_ORE_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, SILVER_ORE_BURIED_PLACED_KEY);

        ServerTickEvents.END_SERVER_TICK.register(ArtificialCloudBlockEntity::serverTick);

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            MainCommand.register(dispatcher);
        }));

        MolangQueries.<LabrasteelBatteryBlockEntity>setActorVariable("query.stormsoul_capacity_percentage", actor -> 1-(actor.animatable().getStoredStormsoul()/actor.animatable().getMaxStoredStormsoul()));
        MolangQueries.<LabrasteelChargerBlockEntity>setActorVariable("query.stormsoul_spin_time", actor -> {
            if (actor.animatable() instanceof LabrasteelChargerBlockEntity entity) return MathHelper.lerp(actor.partialTick(), entity.getLastSpinTime(), entity.getSpinTime());
            else return actor.animationTicks()*8;
        });

        LOGGER.info("Common initialisation complete, enjoy!");
    }
}
