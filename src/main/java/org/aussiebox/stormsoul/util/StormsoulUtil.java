package org.aussiebox.stormsoul.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;

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

}
