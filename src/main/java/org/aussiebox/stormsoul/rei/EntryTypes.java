package org.aussiebox.stormsoul.rei;

import me.shedaniel.rei.api.common.entry.type.EntryType;
import net.minecraft.block.BlockState;
import org.aussiebox.stormsoul.Stormsoul;

public interface EntryTypes {
    EntryType<BlockState> BLOCK_STATE = EntryType.deferred(Stormsoul.id("blockstate"));
}
