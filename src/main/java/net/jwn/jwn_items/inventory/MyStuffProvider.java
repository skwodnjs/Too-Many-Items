package net.jwn.jwn_items.inventory;

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

public class MyStuffProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<MyStuff> myStuffCapability = CapabilityManager.get(new CapabilityToken<MyStuff>() {});

    private MyStuff myStuff = null;

    private final LazyOptional<MyStuff> optional = LazyOptional.of(this::createMyStuff);

    private MyStuff createMyStuff() {
        if (this.myStuff == null) {
            this.myStuff = new MyStuff();
        }
        return this.myStuff;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == myStuffCapability) {
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
