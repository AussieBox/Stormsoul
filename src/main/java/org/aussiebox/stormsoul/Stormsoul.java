package org.aussiebox.stormsoul;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.blockentity.custom.ArtificialCloudBlockEntity;
import org.aussiebox.stormsoul.component.ModDataComponentTypes;
import org.aussiebox.stormsoul.item.ModItems;
import org.aussiebox.stormsoul.recipe.ModRecipes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static final RegistryKey<PlacedFeature> LABRADORITE_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, id("ore_labradorite"));
    public static final RegistryKey<PlacedFeature> LABRADORITE_ORE_BURIED_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, id("ore_labradorite_buried"));

    public static List<String> initMessages = new ArrayList<>();

    @Override
    public void onInitialize() {
        initMessages.add("Cooking up a storm...");
        initMessages.add("Preparing elec-teriffic technology...");
        initMessages.add("Did anyone let the souls out?");
        LOGGER.info(initMessages.get(RANDOM.nextInt(initMessages.size())));

        ModBlocks.init();
        ModBlockEntities.init();
        ModItems.init();
        ModDataComponentTypes.init();
        ModRecipes.init();

        Registry.register(Registries.PARTICLE_TYPE, id("artificial_cloud_spark"), ARTIFICIAL_CLOUD_SPARK);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, LABRADORITE_ORE_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, LABRADORITE_ORE_BURIED_PLACED_KEY);

        ServerTickEvents.END_SERVER_TICK.register(ArtificialCloudBlockEntity::serverTick);

        LOGGER.info("Common initialisation complete, enjoy!");
    }
}
