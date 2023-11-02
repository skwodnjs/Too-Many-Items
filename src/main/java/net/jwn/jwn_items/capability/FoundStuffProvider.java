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

public class FoundStuffProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<FoundStuff> foundStuffCapability = CapabilityManager.get(new CapabilityToken<FoundStuff>() {});

    private FoundStuff foundStuff = null;

    private final LazyOptional<FoundStuff> optional = LazyOptional.of(this::createMyStuff);

    private FoundStuff createMyStuff() {
        if (this.foundStuff == null) {
            this.foundStuff = new FoundStuff();
        }
        return this.foundStuff;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == foundStuffCapability) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createMyStuff().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createMyStuff().loadNBTData(nbt);
    }
}
