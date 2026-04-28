package org.aussiebox.stormsoul.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import org.aussiebox.stormsoul.Stormsoul;
import org.aussiebox.stormsoul.block.ModBlocks;

import java.util.function.Function;

public class ModItems {

    public static final Item LABRADORITE = registerItem(
            "labradorite",
            Item::new,
            new Item.Settings()
    );

    public static final RegistryKey<ItemGroup> ITEMGROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Stormsoul.id(Stormsoul.MOD_ID));
    public static final ItemGroup ITEMGROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.STORM_ROD))
            .displayName(Text.translatable("itemGroup.stormsoul.stormsoul"))
            .build();

    public static Item registerItem(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Stormsoul.id(name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static BlockItem registerBlockItem(String name, Function<Item.Settings, BlockItem> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Stormsoul.id(name));
        BlockItem item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, ITEMGROUP_KEY, ITEMGROUP);
        ItemGroupEvents.modifyEntriesEvent(ITEMGROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModBlocks.STORM_ROD);
            itemGroup.add(ModBlocks.ARTIFICIAL_CLOUD);
            itemGroup.add(ModBlocks.LABRADORITE_ORE);
            itemGroup.add(ModBlocks.DEEPSLATE_LABRADORITE_ORE);
            itemGroup.add(LABRADORITE);
            itemGroup.add(ModBlocks.COPPER_LABRADORITE_BATTERY);
        });
    }

}
