package org.aussiebox.stormsoul.client.rei.category;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.rei.EntryTypes;
import org.aussiebox.stormsoul.rei.ModREIPlugin;
import org.aussiebox.stormsoul.rei.display.ControlledShockREIDisplay;

import java.util.ArrayList;
import java.util.List;

public class ControlledShockREICategory implements DisplayCategory<ControlledShockREIDisplay> {
    @Override
    public CategoryIdentifier<? extends ControlledShockREIDisplay> getCategoryIdentifier() {
        return ModREIPlugin.CONTROLLED_SHOCK;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category.rei.stormsoul.controlled_shock");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.STORM_ROD);
    }

    @Override
    public List<Widget> setupDisplay(ControlledShockREIDisplay display, Rectangle bounds) {
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        Text text = Text.translatable("category.rei.stormsoul.controlled_shock.ingredients");
        widgets.add(Widgets.createLabel(new Point(bounds.x + 5 + (MinecraftClient.getInstance().textRenderer.getWidth(text)/2), bounds.y + 5), text));
        widgets.add(Widgets.createSlot(new Point(bounds.getMaxX() - 54 + 12, bounds.getMaxY() - 54 - 21 - 6)).entry(EntryStack.of(EntryTypes.BLOCK_STATE, Blocks.LIGHTNING_ROD.getDefaultState())));

        int blockX = -1;
        int blockY = 0;
        int itemX = -1;
        int itemY = 0;
        List<Slot> slots = new ArrayList<>();
        List<EntryIngredient> input = display.getInputEntries();
        for (EntryIngredient ingredient : input) {
            if (ingredient.getFirst().getType() == EntryTypes.BLOCK_STATE) {
                blockX++;
                if (blockX > 2) {
                    blockX = 0;
                    blockY++;
                }
                slots.add(Widgets.createSlot(new Point(bounds.getMaxX() - 54 - 6 + (blockX * 18), bounds.getMaxY() - 54 - 6 + (blockY * 18))).markInput().entries(ingredient));
            } else {
                itemX++;
                if (itemX > 2) {
                    itemX = 0;
                    itemY++;
                }
                slots.add(Widgets.createSlot(new Point(bounds.x + 6 + (itemX * 18), bounds.y + 17 + (itemY * 18))).markInput().entries(ingredient));
            }
        }

        widgets.addAll(slots);
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 100;
    }
}
