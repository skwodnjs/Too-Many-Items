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

public class CoolTimeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<CoolTime> coolTimeCapability = CapabilityManager.get(new CapabilityToken<CoolTime>() {});

    private CoolTime coolTime = null;

    private final LazyOptional<CoolTime> optional = LazyOptional.of(this::createCoolTime);

    private CoolTime createCoolTime() {
        if (this.coolTime == null) {
            this.coolTime = new CoolTime();
        }
        return this.coolTime;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == coolTimeCapability) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createCoolTime().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCoolTime().loadNBTData(nbt);
    }
}
