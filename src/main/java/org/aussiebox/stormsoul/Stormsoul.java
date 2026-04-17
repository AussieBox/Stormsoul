package org.aussiebox.stormsoul;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.blockentity.ModBlockEntities;
import org.aussiebox.stormsoul.component.ModDataComponentTypes;
import org.aussiebox.stormsoul.item.ModItems;
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

        LOGGER.info("Common initialisation complete, enjoy!");

    }
}
