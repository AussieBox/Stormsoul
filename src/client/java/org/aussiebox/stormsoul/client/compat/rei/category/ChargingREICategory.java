package org.aussiebox.stormsoul.client.compat.rei.category;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.aussiebox.stormsoul.block.ModBlocks;
import org.aussiebox.stormsoul.compat.rei.ModREIPlugin;
import org.aussiebox.stormsoul.compat.rei.display.ChargingREIDisplay;

import java.util.List;

public class ChargingREICategory implements DisplayCategory<ChargingREIDisplay> {
    @Override
    public CategoryIdentifier<? extends ChargingREIDisplay> getCategoryIdentifier() {
        return ModREIPlugin.CHARGING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category.rei.stormsoul.charging");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.LABRASTEEL_CHARGER);
    }

    @Override
    public List<Widget> setupDisplay(ChargingREIDisplay display, Rectangle bounds) {
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.wrapRenderer(bounds, (graphics, bounds1, mouseX, mouseY, delta) -> {
            graphics.drawItem(new ItemStack(ModBlocks.LABRASTEEL_CLAMP), bounds1.getCenterX()-32, bounds1.getCenterY()+20, 0);
            graphics.drawItem(new ItemStack(ModBlocks.LABRASTEEL_CHARGER), bounds1.getCenterX()-32, bounds1.getCenterY()-20, 0);
        }));

        widgets.add(Widgets.createSlot(new Point(bounds.getCenterX()-32, bounds.getCenterY())).entries(display.getInputEntries().getFirst()).markInput());

        widgets.add(Widgets.createLabel(new Point(bounds.getCenterX(), bounds.getCenterY() - 24 - MinecraftClient.getInstance().textRenderer.fontHeight), Text.translatable("category.rei.stormsoul.charging.stormsoul", display.getStormsoul()).withColor(0x55FFFF)));
        widgets.add(Widgets.createArrow(new Point(bounds.getCenterX()-12, bounds.getCenterY())).animationDurationMS(8000));

        widgets.add(Widgets.createSlot(new Point(bounds.getCenterX()+16, bounds.getCenterY())).entries(display.getOutputEntries().getFirst()).markOutput());

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }
}
