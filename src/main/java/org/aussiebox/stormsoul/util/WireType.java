package org.aussiebox.stormsoul.util;

import lombok.Getter;
import net.minecraft.util.StringIdentifiable;

public enum WireType implements StringIdentifiable {
    COPPER("copper", 100, 2.0F, 1f, 0.05F, 0.8F),
    IRON("iron", 90, 1.75F, 1f, 0.05F, 0.9F);

    private final String id;
    @Getter private final int ticksPerTransferStart;
    @Getter private final float transferDurationPerSegment;
    @Getter private final float amountPerTransfer;
    @Getter private final float thickness;
    @Getter private final float sag; // Lord forbid anyone checks my source code, I am no longer pure of heart and soul

    WireType(String id, int ticksPerTransferStart, float transferDurationPerSegment, float amountPerTransfer, float visualThickness, float visualSag) {
        this.id = id;
        this.ticksPerTransferStart = ticksPerTransferStart;
        this.transferDurationPerSegment = transferDurationPerSegment;
        this.amountPerTransfer = amountPerTransfer;
        this.thickness = visualThickness;
        this.sag = visualSag;
    }

    @Override
    public String asString() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
