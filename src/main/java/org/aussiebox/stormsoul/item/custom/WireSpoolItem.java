package org.aussiebox.stormsoul.item.custom;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.blockentity.custom.WireConnectorBlockEntity;
import org.aussiebox.stormsoul.component.ModDataComponentTypes;
import org.aussiebox.stormsoul.util.WireType;

import java.util.Optional;

public class WireSpoolItem extends Item {
    public WireSpoolItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockEntity entity = world.getBlockEntity(context.getBlockPos());
        if (!(entity instanceof WireConnectorBlockEntity wireEntity)) return super.useOnBlock(context);

        if (!context.getStack().contains(ModDataComponentTypes.EDITING_WIRE_AT)) {
            if (wireEntity.getEditor().isEmpty()) {
                wireEntity.setEditor(Optional.ofNullable(context.getPlayer()));
                wireEntity.setWireType(Optional.ofNullable(context.getStack().getOrDefault(ModDataComponentTypes.WIRE_TYPE, WireType.COPPER)));
                context.getStack().set(ModDataComponentTypes.EDITING_WIRE_AT, wireEntity.getPos());
                return ActionResult.SUCCESS;
            } else if (wireEntity.getEditor().get() == context.getPlayer()) {
                wireEntity.setEditor(Optional.empty());
                wireEntity.setWireType(Optional.empty());
                context.getStack().remove(ModDataComponentTypes.EDITING_WIRE_AT);
                return ActionResult.SUCCESS;
            }
        } else {
            BlockEntity linkingTo = world.getBlockEntity(context.getStack().get(ModDataComponentTypes.EDITING_WIRE_AT));
            if (!(linkingTo instanceof WireConnectorBlockEntity linkingToEntity)) return super.useOnBlock(context);

            linkingToEntity.setEditor(Optional.empty());
            linkingToEntity.setConnectedTo(Optional.of(Vec3d.of(entity.getPos())));
            context.getStack().remove(ModDataComponentTypes.EDITING_WIRE_AT);
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }
}
