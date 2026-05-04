package org.aussiebox.stormsoul.client.rei.entryrenderer;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.state.property.Property;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Language;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class BlockStateEntryRenderer implements EntryRenderer<BlockState> {
    private static final ReferenceSet<Block> SEARCH_BLACKLISTED = new ReferenceOpenHashSet<>();

    @Environment(EnvType.CLIENT)
    private List<Text> tryGetItemStackToolTip(EntryStack<BlockState> entry, BlockState value, TooltipContext context) {
        if (!SEARCH_BLACKLISTED.contains(value.getBlock()))
            try {
                return value.getBlock().asItem().getDefaultStack().getTooltip(context.vanillaContext(), MinecraftClient.getInstance().player, context.getFlag());
            } catch (Throwable e) {
                if (context.isSearch()) throw e;
                e.printStackTrace();
                SEARCH_BLACKLISTED.add(value.getBlock());
            }
        return Lists.newArrayList(entry.asFormattedText(context));
    }

    @Override
    public void render(EntryStack<BlockState> entry, DrawContext graphics, Rectangle bounds, int mouseX, int mouseY, float delta) {
        if (!entry.isEmpty()) {
            BlockState value = entry.getValue();
            graphics.getMatrices().pushMatrix();
            graphics.getMatrices().translate(bounds.x, bounds.y);
            graphics.getMatrices().scale(bounds.getWidth() / 16f, bounds.getHeight() / 16f);
            graphics.drawItem(value.getBlock().asItem().getDefaultStack(), 0, 0);
            graphics.drawStackOverlay(MinecraftClient.getInstance().textRenderer, value.getBlock().asItem().getDefaultStack(), 0, 0);
            graphics.getMatrices().popMatrix();
        }
    }

    @Override
    @Nullable
    public Tooltip getTooltip(EntryStack<BlockState> entry, TooltipContext context) {
        if (entry.isEmpty())
            return null;
        Tooltip tooltip = Tooltip.create();
        Optional<TooltipData> component = entry.getValue().getBlock().asItem().getTooltipData(entry.getValue().getBlock().asItem().getDefaultStack());
        List<Text> components = tryGetItemStackToolTip(entry, entry.getValue(), context);
        if (!components.isEmpty()) {
            tooltip.add(components.getFirst());
        }
        component.ifPresent(tooltip::add);
        for (int i = 1; i < components.size(); i++) {
            tooltip.add(components.get(i));
        }
        boolean newline = false;
        for (Property<?> property : entry.getValue().getProperties()) {
            Optional<?> value = entry.getValue().getOrEmpty(property);
            if (value.isEmpty()) continue;

            if (!newline) {
                tooltip.add(Text.of(" "));
                newline = true;
            }

            MutableText text = Text.empty();
            if (Language.getInstance().hasTranslation("block.property." + property.getName())) text.append(Text.translatable("block.property." + property.getName()).withColor(0xAAAAAA));
            else text.append(Text.literal(property.getName()).withColor(0xAAAAAA));
            text.append(": ");
            switch (value.get()) {
                case String string -> {
                    if (Language.getInstance().hasTranslation("block.property." + property.getName() + "." + string))
                        text.append(Text.translatable("block.property." + property.getName() + "." + string).withColor(0x55FFFF));
                    else text.append(Text.literal(string).withColor(0x55FFFF));
                }
                case Integer integer -> text.append(Text.literal(String.valueOf(integer)).withColor(0xFF5555));
                case Enum<?> enumeration -> {
                    if (Language.getInstance().hasTranslation("block.property." + property.getName() + "." + enumeration.toString()))
                        text.append(Text.translatable("block.property." + property.getName() + "." + enumeration.toString()).withColor(0xC68AFF));
                    else text.append(Text.literal(enumeration.toString()).withColor(0xC68AFF));
                }
                case Boolean bool -> text.append(Text.translatable("block.property.boolean." + bool).withColor(bool ? 0x55FF55 : 0xFF5555));
                default -> text.append(Text.literal(String.valueOf(value.get())).withColor(0xAAAAAA));
            }
        }
        return tooltip.withTooltipStyle(entry.getValue().getBlock().asItem().getDefaultStack().get(DataComponentTypes.TOOLTIP_STYLE));
    }
}
