package org.aussiebox.stormsoul.util;

import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StormsoulUtil {

    public static double smoothInterpolate(double start, double end, double time, boolean clamp) {
        if (clamp) time = Math.max(0.0, Math.min(1.0, time));
        double smoothT = time * time * (3 - 2 * time);
        return start + (end - start) * smoothT;
    }

    public static List<Pair<Item, MutableInt>> condenseStacks(List<ItemStack> stacks) {
        List<Pair<Item, MutableInt>> actualItems = new ArrayList<>();
        for (ItemStack stack : stacks) {
            boolean existed = false;
            for (Pair<Item, MutableInt> pair : actualItems) {
                Item actualItem = pair.getLeft();
                if (stack.isOf(actualItem)) {
                    pair.getRight().add(stack.getCount());
                    existed = true;
                    break;
                }
            }
            if (!existed) actualItems.add(new Pair<>(stack.getItem(), new MutableInt(stack.getCount())));
        }
        return actualItems;
    }

    public static MutableText createMovingGradient(String text, int colorStart, int colorEnd) {
        MutableText result = Text.empty();
        int length = text.length();

        long time = System.currentTimeMillis();
        float phase = (time % 2000L) / 2000.0f;

        for (int i = 0; i < length; ++i) {
            float charOffset = (float) i / (float) Math.max(1, length);

            float progress = (float) (Math.sin((phase + charOffset) * Math.PI * 2) * 0.5 + 0.5);

            int currentColor = interpolateColor(colorStart, colorEnd, progress);
            MutableText part = Text.literal(String.valueOf(text.charAt(i)))
                    .styled((style) -> style.withColor(currentColor).withItalic(false));
            result.append(part);
        }

        return result;
    }

    private static int interpolateColor(int colorStart, int colorEnd, float progress) {
        int r1 = colorStart >> 16 & 255;
        int g1 = colorStart >> 8 & 255;
        int b1 = colorStart & 255;
        int r2 = colorEnd >> 16 & 255;
        int g2 = colorEnd >> 8 & 255;
        int b2 = colorEnd & 255;
        int r = (int)((float)r1 + (float)(r2 - r1) * progress);
        int g = (int)((float)g1 + (float)(g2 - g1) * progress);
        int b = (int)((float)b1 + (float)(b2 - b1) * progress);
        return (r << 16) + (g << 8) + b;
    }

    public static Vec2f faceVecToVec(Vec3d source, Vec3d target) {
        double diffX = target.x - source.x;
        double diffY = target.y - source.y;
        double diffZ = target.z - source.z;

        double distanceXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F);
        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, distanceXZ)));

        return new Vec2f(pitch, yaw);
    }

    public static Map<Direction, VoxelShape> createUpwardFacingShapeMap(VoxelShape shape) {
        return createUpwardFacingShapeMap(shape, new Vec3d(0.5, 0.5, 0.5));
    }

    public static Map<Direction, VoxelShape> createUpwardFacingShapeMap(VoxelShape shape, Vec3d anchor) {
        return Maps.newEnumMap(
                Map.of(
                        Direction.NORTH,
                        VoxelShapes.transform(shape, DirectionTransformation.fromRotations(AxisRotation.R270, AxisRotation.R180), anchor),
                        Direction.EAST,
                        VoxelShapes.transform(shape, DirectionTransformation.ROT_90_Z_NEG, anchor),
                        Direction.SOUTH,
                        VoxelShapes.transform(shape, DirectionTransformation.fromRotations(AxisRotation.R90, AxisRotation.R180), anchor),
                        Direction.WEST,
                        VoxelShapes.transform(shape, DirectionTransformation.ROT_90_Z_POS, anchor),
                        Direction.UP,
                        shape,
                        Direction.DOWN,
                        VoxelShapes.transform(shape, DirectionTransformation.fromRotations(AxisRotation.R180, AxisRotation.R0), anchor)
                )
        );
    }

}
