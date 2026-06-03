package org.aussiebox.stormsoul.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.component.ModDataComponentTypes;
import org.aussiebox.stormsoul.item.custom.SoulweaverNetItem;
import org.aussiebox.stormsoul.item.custom.StormsoulIlluminosItem;
import org.aussiebox.stormsoul.item.custom.WireSpoolItem;
import org.aussiebox.stormsoul.item.custom.geckolib.ConnectedCasingItem;
import org.aussiebox.stormsoul.item.custom.geckolib.LabrasteelChargerItem;
import org.aussiebox.stormsoul.item.custom.geckolib.StormRodItem;
import org.aussiebox.stormsoul.util.WireType;

import java.util.function.Function;

public class ModItems {
    public static final ToolMaterial STORMSOUL;

    static {
        STORMSOUL = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 5012, 10.0F, 5.0F, 20, TagKey.of(RegistryKeys.ITEM, Stormsoul.id("stormsoul_tool_materials")));
    }

    public static StormRodItem STORM_ROD = registerBlockItem(
            "storm_rod",
            StormRodItem::new,
            new Item.Settings()
    );

    public static final Item LABRADORITE = registerItem(
            "labradorite",
            Item::new,
            new Item.Settings()
    );

    public static final Item LABRASTEEL_INGOT = registerItem(
            "labrasteel_ingot",
            Item::new,
            new Item.Settings()
    );

    public static final Item SOULWEAVER_NET = registerItem(
            "soulweaver_net",
            SoulweaverNetItem::new,
            new Item.Settings()
                    .rarity(Rarity.UNCOMMON)
                    .repairable(Items.STRING)
    );

    public static final StormsoulIlluminosItem STORMSOUL_ILLUMINOS = registerItem(
            "stormsoul_illuminos",
            StormsoulIlluminosItem::new,
            new Item.Settings()
                    .rarity(Rarity.RARE)
                    .sword(STORMSOUL, 3.0F, -2.0F)
    );

    public static final Item COPPER_WIRE_SPOOL = registerItem(
            "copper_wire_spool",
            WireSpoolItem::new,
            new Item.Settings()
                    .component(ModDataComponentTypes.WIRE_TYPE, WireType.COPPER)
    );

    public static final Item IRON_WIRE_SPOOL = registerItem(
            "iron_wire_spool",
            WireSpoolItem::new,
            new Item.Settings()
                    .component(ModDataComponentTypes.WIRE_TYPE, WireType.IRON)
    );

    public static final Item IMPURE_RAW_SILVER = registerItem(
            "impure_raw_silver",
            Item::new,
            new Item.Settings()
    );

    public static final Item PURE_RAW_SILVER = registerItem(
            "pure_raw_silver",
            Item::new,
            new Item.Settings()
    );

    public static final Item SILVER_INGOT = registerItem(
            "silver_ingot",
            Item::new,
            new Item.Settings()
    );

    public static LabrasteelChargerItem LABRASTEEL_CHARGER = registerBlockItem(
            "labrasteel_charger",
            LabrasteelChargerItem::new,
            new Item.Settings()
    );

    public static ConnectedCasingItem SILVER_CASING = registerBlockItem(
            "silver_casing",
            (settings) -> new ConnectedCasingItem(ModBlocks.SILVER_CASING, settings),
            new Item.Settings()
    );

    public static final RegistryKey<ItemGroup> ITEMGROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Stormsoul.id(Stormsoul.MOD_ID));
    public static final ItemGroup ITEMGROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.STORM_ROD))
            .displayName(Text.translatable("itemGroup.stormsoul.stormsoul"))
            .build();

    public static <I extends Item> I registerItem(String name, Function<Item.Settings, I> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Stormsoul.id(name));
        I item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static <I extends BlockItem> I registerBlockItem(String name, Function<Item.Settings, I> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Stormsoul.id(name));
        I item = itemFactory.apply(settings.registryKey(itemKey).useBlockPrefixedTranslationKey());
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, ITEMGROUP_KEY, ITEMGROUP);
        ItemGroupEvents.modifyEntriesEvent(ITEMGROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModBlocks.LABRADORITE_ORE);
            itemGroup.add(ModBlocks.DEEPSLATE_LABRADORITE_ORE);
            itemGroup.add(LABRADORITE);
            itemGroup.add(ModBlocks.LABRADORITE_BLOCK);
            itemGroup.add(ModBlocks.LABRADORITE_GEODE);
            itemGroup.add(LABRASTEEL_INGOT);
            itemGroup.add(ModBlocks.LABRASTEEL_BLOCK);
            itemGroup.add(ModBlocks.SMOOTH_LABRASTEEL_BLOCK);
            itemGroup.add(ModBlocks.SILVER_ORE);
            itemGroup.add(ModBlocks.DEEPSLATE_SILVER_ORE);
            itemGroup.add(IMPURE_RAW_SILVER);
            itemGroup.add(ModBlocks.IMPURE_RAW_SILVER_BLOCK);
            itemGroup.add(PURE_RAW_SILVER);
            itemGroup.add(ModBlocks.PURE_RAW_SILVER_BLOCK);
            itemGroup.add(SILVER_INGOT);
            itemGroup.add(ModBlocks.SILVER_CASING);
            itemGroup.add(SOULWEAVER_NET);
            itemGroup.add(ModBlocks.ARTIFICIAL_CLOUD);
            itemGroup.add(ModBlocks.STORM_ROD);
            itemGroup.add(ModBlocks.COPPER_LABRASTEEL_BATTERY);
            itemGroup.add(ModBlocks.IRON_LABRASTEEL_BATTERY);
            itemGroup.add(ModBlocks.WIRE_CONNECTOR);
            itemGroup.add(COPPER_WIRE_SPOOL);
            itemGroup.add(IRON_WIRE_SPOOL);
            itemGroup.add(ModBlocks.LABRASTEEL_CHARGER);
            itemGroup.add(STORMSOUL_ILLUMINOS);
        });
    }

}
