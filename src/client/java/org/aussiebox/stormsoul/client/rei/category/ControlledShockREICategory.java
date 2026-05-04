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
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.rei.ModREIPlugin;
import org.aussiebox.stormsoul.rei.display.ControlledShockREIDisplay;

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
        Point startPoint = new Point(bounds.getCenterX() - 58, bounds.getCenterY() - 27);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 60, startPoint.y + 18)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 95, startPoint.y + 19)));

        List<Slot> slots = Lists.newArrayList();
        List<EntryIngredient> input = display.getInputEntries();
        int x = 0;
        int y = 0;
        for (EntryIngredient ingredient : input) {
            x++;
            if (x > 3) {
                x = 1;
                y++;
            }

            slots.add(Widgets.createSlot(new Point(startPoint.x + 1 + x * 18, startPoint.y + 1 + y * 18)).markInput().entries(ingredient));
        }

        widgets.addAll(slots);
        return widgets;
    }

    @Override
    public int getDisplayWidth(ControlledShockREIDisplay display) {
        return DisplayCategory.super.getDisplayWidth(display);
    }

    @Override
    public int getDisplayHeight() {
        return 100;
    }
}
