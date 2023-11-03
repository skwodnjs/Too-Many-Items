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

public class PlayerStatProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerStat> playerStatsCapability = CapabilityManager.get(new CapabilityToken<PlayerStat>() {});

    private PlayerStat playerStats = null;

    private final LazyOptional<PlayerStat> optional = LazyOptional.of(this::createPlayerStats);

    private PlayerStat createPlayerStats() {
        if (this.playerStats == null) {
            this.playerStats = new PlayerStat();
        }
        return this.playerStats;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == playerStatsCapability) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerStats().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerStats().loadNBTData(nbt);
    }
}