package org.aussiebox.stormsoul.blockentity.custom;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.aussiebox.stormsoul.Stormsoul;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractStormsoulBlockEntity extends BlockEntity {
    private double maxStoredStormsoul = 256;
    private double storedStormsoul = 0;
    private double transferPerTick = 1;
    private List<Direction> inputDirections = new ArrayList<>();
    private List<Direction> outputDirections = new ArrayList<>();
    private List<Direction> transferDirections = new ArrayList<>();
    private TransferMethod transferMethod = TransferMethod.SPLIT;

    public AbstractStormsoulBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public double getMaxStoredStormsoul() {
        return maxStoredStormsoul;
    }
    public double getStoredStormsoul() {
        return storedStormsoul;
    }
    public double getTransferPerTick() {
        return transferPerTick;
    }
    public List<Direction> getOutputDirections() {
        return outputDirections;
    }
    public List<Direction> getInputDirections() {
        return inputDirections;
    }
    public List<Direction> getTransferDirections() {
        return transferDirections;
    }
    public TransferMethod getTransferMethod() {
        return transferMethod;
    }

    public void setMaxStoredStormsoul(double amount) {
        maxStoredStormsoul = amount;
        markDirty();
    }
    public void setTransferPerTick(double amount) {
        transferPerTick = amount;
        markDirty();
    }
    public void setOutputDirections(Direction... directions) {
        outputDirections = new ArrayList<>(List.of(directions));
        markDirty();
    }
    public void setInputDirections(Direction... directions) {
        inputDirections = new ArrayList<>(List.of(directions));
        markDirty();
    }
    /**
     * Sets the directions, in order, for the current TransferMethod to use.<p>
     * Behavior varies depending on current TransferMethod.<p>
     * Ignored if current TransferMethod does not require a direction input.
     * @see org.aussiebox.stormsoul.blockentity.custom.AbstractStormsoulBlockEntity.TransferMethod
     * @since 0.1.0
     */
    public void setTransferDirections(Direction... directions) {
        if (transferMethod.directionInputMin == 0 && transferMethod.directionInputMax == 0) {
            Stormsoul.LOGGER.error("Transfer Method {} does not allow for any direction inputs", transferMethod.id);
            return;
        }
        if (directions.length < transferMethod.directionInputMin) {
            Stormsoul.LOGGER.error("Transfer Method {} requires a minimum of {} direction inputs, only {} were given", transferMethod.id, transferMethod.directionInputMin, directions.length);
            return;
        }
        if (directions.length > transferMethod.directionInputMax) {
            Stormsoul.LOGGER.error("Transfer Method {} allows a maximum of {} direction inputs, {} were given", transferMethod.id, transferMethod.directionInputMax, directions.length);
            return;
        }
        transferDirections = new ArrayList<>(List.of(directions));
        markDirty();
    }
    public void setTransferMethod(TransferMethod method) {
        transferMethod = method;
        markDirty();
    }

    public double setStored(double amount) {
        storedStormsoul = Math.min(storedStormsoul + amount, getMaxStoredStormsoul());
        markDirty();
        return storedStormsoul;
    }

    /**
     * Adds the given amount of Stormsoul to the block entity's storage.<p>
     * Will not add more than the storage cap.
     * @return Total amount added
     * @since 0.1.0
     */
    public double addStored(double amount) {
        double oldStoredStormsoul = storedStormsoul;
        setStored(Math.min(oldStoredStormsoul + amount, getMaxStoredStormsoul() - oldStoredStormsoul));
        Stormsoul.LOGGER.info("{} {}", Math.min(oldStoredStormsoul + amount, getMaxStoredStormsoul() - oldStoredStormsoul), getNameForReport());
        return Math.min(oldStoredStormsoul + amount, getMaxStoredStormsoul() - oldStoredStormsoul);
    }

    /**
     * Removes the given amount of Stormsoul from the block entity's storage.<p>
     * Will not remove more than the current amount stored.
     * @return Total amount removed
     * @since 0.1.0
     */
    public double removeStored(double amount) {
        double oldStoredStormsoul = storedStormsoul;
        setStored(Math.max(oldStoredStormsoul - amount, 0));
        return Math.max(oldStoredStormsoul - amount, 0);
    }

    /**
     * Transfers Stormsoul to another BlockEntity.<p>
     * Will not transfer too much, nor too little.
     * @param to The blockstate to add Stormsoul to
     * @return The amount of Stormsoul successfully transferred
     * @since 0.1.0
     */
    public double transferStored(BlockEntity to, double amount) {
        if (!(to instanceof AbstractStormsoulBlockEntity toEntity)) return 0;
        double add = removeStored(Math.min(toEntity.storedStormsoul + amount, toEntity.getMaxStoredStormsoul()));
        return toEntity.addStored(add);
    }

    /**
     * Manages the core data for Stormsoul block entities.<p>
     * Should be called inside predecessors' ticking methods.<p>
     * @since 0.1.0
     */
    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof AbstractStormsoulBlockEntity stormsoulEntity)) return;

        switch (stormsoulEntity.transferMethod) {
            case SPLIT -> {
                List<Direction> validDirections = new ArrayList<>();
                for (Direction direction : stormsoulEntity.getOutputDirections()) {
                    if (world.getBlockEntity(pos.offset(direction)) instanceof AbstractStormsoulBlockEntity stormsoulNeighbor) {
                        if (!stormsoulNeighbor.getInputDirections().contains(direction.getOpposite())) return;
                        validDirections.add(direction);
                    }
                }
                if (validDirections.isEmpty()) break;
                double amount = stormsoulEntity.transferPerTick/validDirections.size();
                for (Direction direction : validDirections)
                    stormsoulEntity.transferToNeighbor(world, pos, direction, amount);
            }
            case PRIORITISE_THEN_SPLIT -> {
                List<Direction> prioritisedDirections = new ArrayList<>();
                List<Direction> validDirections = new ArrayList<>();
                for (Direction direction : stormsoulEntity.getOutputDirections()) {
                    if (world.getBlockEntity(pos.offset(direction)) instanceof AbstractStormsoulBlockEntity stormsoulNeighbor) {
                        if (!stormsoulNeighbor.getInputDirections().contains(direction.getOpposite())) return;
                        if (stormsoulEntity.getTransferDirections().contains(direction)) prioritisedDirections.add(direction);
                        else validDirections.add(direction);
                    }
                }
                if (validDirections.isEmpty() && prioritisedDirections.isEmpty()) break;

                Map<Direction, Integer> positions = IntStream.range(0, stormsoulEntity.transferDirections.size())
                        .boxed()
                        .collect(Collectors.toMap(stormsoulEntity.transferDirections::get, i -> i));
                prioritisedDirections.sort(Comparator.comparingInt(key -> positions.getOrDefault(key, Integer.MAX_VALUE)));
                for (Direction direction : prioritisedDirections)
                    stormsoulEntity.transferToNeighbor(world, pos, direction, stormsoulEntity.transferPerTick);

                double amount = stormsoulEntity.transferPerTick/validDirections.size();
                for (Direction direction : validDirections)
                    stormsoulEntity.transferToNeighbor(world, pos, direction, amount);
            }
            case IN_ORDER -> {
                List<Direction> directions = stormsoulEntity.getOutputDirections();
                Map<Direction, Integer> positions = IntStream.range(0, stormsoulEntity.transferDirections.size())
                        .boxed()
                        .collect(Collectors.toMap(stormsoulEntity.transferDirections::get, i -> i));
                directions.sort(Comparator.comparingInt(key -> positions.getOrDefault(key, Integer.MAX_VALUE)));

                for (Direction direction : directions)
                    stormsoulEntity.transferToNeighbor(world, pos, direction, stormsoulEntity.getTransferPerTick());
            }
            case RANDOM_ALL -> {
                List<Direction> directions = stormsoulEntity.getOutputDirections();
                Collections.shuffle(directions);
                for (Direction direction : directions)
                    stormsoulEntity.transferToNeighbor(world, pos, direction, stormsoulEntity.getTransferPerTick());
            }
            case RANDOM_ONE -> {
                List<Direction> directions = stormsoulEntity.getOutputDirections();
                Collections.shuffle(directions);
                if (directions.isEmpty()) break;
                stormsoulEntity.transferToNeighbor(world, pos, directions.getFirst(), stormsoulEntity.getTransferPerTick());
            }
            default -> {
                for (Direction direction : stormsoulEntity.getOutputDirections())
                    stormsoulEntity.transferToNeighbor(world, pos, direction, stormsoulEntity.getTransferPerTick());
            }
        }
    }

    public double transferToNeighbor(World world, BlockPos pos, Direction direction, double amount) {
        if (world.getBlockEntity(pos.offset(direction)) instanceof AbstractStormsoulBlockEntity stormsoulNeighbor) {
            if (!stormsoulNeighbor.getInputDirections().contains(direction.getOpposite())) {
                Stormsoul.LOGGER.warn("Block in direction {} of output does not have input connecting", direction);
                return 0;
            }
            return transferStored(stormsoulNeighbor, amount);
        }
        Stormsoul.LOGGER.warn("Block in direction {} of output is not recognised as correct type", direction);
        return 0;
    }

    @Override
    protected void readData(ReadView tag) {
        maxStoredStormsoul = tag.getDouble("maxStoredStormsoul", 0);
        storedStormsoul = tag.getDouble("storedStormsoul", 0);
        transferPerTick = tag.getDouble("transferPerTick", 0);
        Optional<List<Direction>> list = tag.read("inputDirections", Codec.list(Direction.CODEC));
        inputDirections = list.<List<Direction>>map(ArrayList::new).orElseGet(ArrayList::new);
        list = tag.read("outputDirections", Codec.list(Direction.CODEC));
        outputDirections = list.<List<Direction>>map(ArrayList::new).orElseGet(ArrayList::new);
        list = tag.read("transferDirections", Codec.list(Direction.CODEC));
        transferDirections = list.<List<Direction>>map(ArrayList::new).orElseGet(ArrayList::new);
        Optional<TransferMethod> method = tag.read("transferMethod", TransferMethod.CODEC);
        method.ifPresent(value -> transferMethod = value);
    }

    @Override
    protected void writeData(WriteView tag) {
        tag.putDouble("maxStoredStormsoul", maxStoredStormsoul);
        tag.putDouble("storedStormsoul", storedStormsoul);
        tag.putDouble("transferPerTick", transferPerTick);
        tag.put("inputDirections", Codec.list(Direction.CODEC), inputDirections);
        tag.put("outputDirections", Codec.list(Direction.CODEC), outputDirections);
        tag.put("transferDirections", Codec.list(Direction.CODEC), transferDirections);
        tag.put("transferMethod", TransferMethod.CODEC, transferMethod);
    }

    public enum TransferMethod implements StringIdentifiable {
        SPLIT("split"),
        PRIORITISE_THEN_SPLIT("prioritise_then_split", 1, 5),
        IN_ORDER("in_order", 1, 6),
        RANDOM_ALL("random_all"),
        RANDOM_ONE("random_one");

        public static final StringIdentifiable.EnumCodec<TransferMethod> CODEC = StringIdentifiable.createCodec(TransferMethod::values);
        private final String id;
        private final int directionInputMin;
        private final int directionInputMax;

        TransferMethod(String id, int directionInputMin, int directionInputMax) {
            this.id = id;
            this.directionInputMin = Math.clamp(directionInputMin, 0, 6);
            this.directionInputMax = Math.clamp(directionInputMax, 0, 6);
        }

        TransferMethod(String id) {
            this.id = id;
            this.directionInputMin = 0;
            this.directionInputMax = 0;
        }

        @Override
        public String asString() {
            return id;
        }
    }
}
