package net.jwn.jwn_items.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerOptionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerOption> playerOptionsCapability = CapabilityManager.get(new CapabilityToken<PlayerOption>() {});

    private PlayerOption playerOptions = null;

    private final LazyOptional<PlayerOption> optional = LazyOptional.of(this::createPlayerOptions);

    private PlayerOption createPlayerOptions() {
        if (this.playerOptions == null) {
            this.playerOptions = new PlayerOption();
        }
        return this.playerOptions;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == playerOptionsCapability) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerOptions().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerOptions().loadNBTData(nbt);
    }
}