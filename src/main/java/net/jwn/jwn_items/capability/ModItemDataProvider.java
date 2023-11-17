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

public class ModItemDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ModItemData> modItemDataCapability = CapabilityManager.get(new CapabilityToken<ModItemData>() {});

    private ModItemData modItemData = null;

    private final LazyOptional<ModItemData> optional = LazyOptional.of(this::createModItemData);

    private ModItemData createModItemData() {
        if (this.modItemData == null) {
            this.modItemData = new ModItemData();
        }
        return this.modItemData;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == modItemDataCapability) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createModItemData().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createModItemData().loadNBTData(nbt);
    }
}
