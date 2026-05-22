package org.aussiebox.stormsoul.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
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
import org.aussiebox.stormsoul.block.custom.LabrasteelBatteryBlock;
import org.aussiebox.stormsoul.block.custom.StormRodBlock;
import org.aussiebox.stormsoul.block.custom.WireConnectorBlock;

import java.util.function.Function;

public class ModBlocks {

    public static Block STORM_ROD = register(
            "storm_rod",
            StormRodBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_BLUE)
                    .requiresTool()
                    .strength(3.0F, 6.0F)
                    .sounds(BlockSoundGroup.COPPER)
                    .nonOpaque()
                    .solid()
    );

    public static final Block ARTIFICIAL_CLOUD = registerWithItem(
            "artificial_cloud",
            ArtificialCloudBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.CLEAR)
                    .strength(3.0F, 1.0F)
                    .sounds(BlockSoundGroup.WOOL)
                    .dynamicBounds()
                    .nonOpaque()
                    .solid()
    );

    public static final Block LABRADORITE_ORE = registerWithItem(
            "labradorite_ore",
            (settings) -> new ExperienceDroppingBlock(UniformIntProvider.create(3, 8), settings),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY)
                    .strength(3.0F, 3.0F)
                    .sounds(BlockSoundGroup.STONE)
                    .requiresTool()
    );

    public static final Block DEEPSLATE_LABRADORITE_ORE = registerWithItem(
            "deepslate_labradorite_ore",
            (settings) -> new ExperienceDroppingBlock(UniformIntProvider.create(3, 8), settings),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DEEPSLATE_GRAY)
                    .strength(4.5F, 3.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE)
                    .requiresTool()
    );

    public static final Block LABRADORITE_BLOCK = registerWithItem(
            "labradorite_block",
            Block::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIAMOND_BLUE)
                    .strength(5.0F, 6.0F)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)
                    .requiresTool()
    );

    public static final Block LABRADORITE_GEODE = registerWithItem(
            "labradorite_geode",
            Block::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIAMOND_BLUE)
                    .strength(5.0F, 6.0F)
                    .sounds(BlockSoundGroup.BASALT)
                    .requiresTool()
    );

    public static final Block LABRASTEEL_BLOCK = registerWithItem(
            "labrasteel_block",
            Block::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.LAPIS_BLUE)
                    .strength(5.0F, 6.0F)
                    .sounds(BlockSoundGroup.METAL)
                    .requiresTool()
    );

    public static final Block COPPER_LABRASTEEL_BATTERY = registerWithItem(
            "copper_labrasteel_battery",
            (settings) -> new LabrasteelBatteryBlock(200, "copper", settings),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.BLACK)
                    .strength(3.0F, 6.0F)
                    .sounds(BlockSoundGroup.METAL)
                    .requiresTool()
                    .nonOpaque()
                    .solid()
    );

    public static final Block IRON_LABRASTEEL_BATTERY = registerWithItem(
            "iron_labrasteel_battery",
            (settings) -> new LabrasteelBatteryBlock(500, "iron", settings),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.BLACK)
                    .strength(3.0F, 6.0F)
                    .sounds(BlockSoundGroup.METAL)
                    .requiresTool()
                    .nonOpaque()
                    .solid()
    );

    public static final Block WIRE_CONNECTOR = registerWithItem(
            "wire_connector",
            WireConnectorBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .strength(1.5F, 3.0F)
                    .sounds(BlockSoundGroup.METAL)
                    .requiresTool()
                    .nonOpaque()
                    .solid()
    );

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static Block registerWithItem(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        RegistryKey<Item> itemKey = keyOfItem(name);
        BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());
        Registry.register(Registries.ITEM, itemKey, blockItem);

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
