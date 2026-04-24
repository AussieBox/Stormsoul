package org.aussiebox.stormsoul.cca;

import com.mojang.serialization.Codec;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.Stormsoul;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.ArrayList;
import java.util.List;

public class ArtificialStormComponent implements AutoSyncedComponent, ServerTickingComponent {
    public static final ComponentKey<ArtificialStormComponent> KEY = ComponentRegistry.getOrCreate(Stormsoul.id("artificial_storm"), ArtificialStormComponent.class);
    private final World world;

    public boolean storming = false;
    public int time = 0;
    public List<Integer> futureTimes = new ArrayList<>();

    public ArtificialStormComponent(World world) {
        this.world = world;
    }

    @Override
    public void serverTick() {
        while (futureTimes.size() < 10) {
            futureTimes.add(Stormsoul.RANDOM.nextInt(8000, 24000));
        }
        if (time <= 0) {
            time = futureTimes.removeFirst();
            storming =! storming;
        }
        time--;
    }

    @Override
    public void readData(ReadView tag) {
        storming = tag.getBoolean("storming", false);
        time = tag.getInt("time", 0);

        if (tag.contains("futureTimes")) futureTimes = tag.read("futureTimes", Codec.list(Codec.INT)).orElseThrow();
        else futureTimes = new ArrayList<>();
    }

    @Override
    public void writeData(WriteView tag) {
        tag.putBoolean("storming", storming);
        tag.putInt("time", time);
        tag.put("futureTimes", Codec.list(Codec.INT), futureTimes);
    }
}
