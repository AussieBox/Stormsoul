package org.aussiebox.stormsoul.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.block.custom.ArtificialCloudBlock;
import org.aussiebox.stormsoul.block.custom.LabradoriteBatteryBlock;
import org.aussiebox.stormsoul.block.custom.StormRodBlock;

import java.util.function.Function;

public class ModBlocks {

    public static final Block STORM_ROD = register(
            "storm_rod",
            StormRodBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_BLUE)
                    .requiresTool()
                    .strength(3.0F, 6.0F)
                    .sounds(BlockSoundGroup.COPPER)
                    .nonOpaque()
                    .solid(),
            true
    );

    public static final Block ARTIFICIAL_CLOUD = register(
            "artificial_cloud",
            ArtificialCloudBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.CLEAR)
                    .strength(3.0F, 1.0F)
                    .sounds(BlockSoundGroup.WOOL)
                    .dynamicBounds()
                    .nonOpaque()
                    .solid(),
            true
    );

    public static final Block LABRADORITE_ORE = register(
            "labradorite_ore",
            (settings) -> new ExperienceDroppingBlock(UniformIntProvider.create(3, 7), settings),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(3.0F, 3.0F)
                    .requiresTool(),
            true
    );

    public static final Block DEEPSLATE_LABRADORITE_ORE = register(
            "deepslate_labradorite_ore",
            (settings) -> new ExperienceDroppingBlock(UniformIntProvider.create(3, 7), settings),
            AbstractBlock.Settings.copyShallow(LABRADORITE_ORE)
                    .mapColor(MapColor.DEEPSLATE_GRAY)
                    .strength(4.5F, 3.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE),
            true
    );

    public static final Block LABRADORITE_BLOCK = register(
            "labradorite_block",
            Block::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.LAPIS_BLUE)
                    .instrument(NoteBlockInstrument.CHIME)
                    .requiresTool()
                    .strength(5.0F, 6.0F)
                    .sounds(BlockSoundGroup.METAL),
            true
    );

    public static final Block COPPER_LABRADORITE_BATTERY = register(
            "copper_labradorite_battery",
            (settings) -> new LabradoriteBatteryBlock(200, settings),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.BLACK)
                    .requiresTool()
                    .strength(3.0F, 6.0F)
                    .sounds(BlockSoundGroup.METAL)
                    .emissiveLighting(LabradoriteBatteryBlock::ifNotEmpty)
                    .nonOpaque()
                    .solid(),
            true
    );

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));
        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = keyOfItem(name);
            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Stormsoul.id(name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Stormsoul.id(name));
    }

    public static void init() {
    }
}
