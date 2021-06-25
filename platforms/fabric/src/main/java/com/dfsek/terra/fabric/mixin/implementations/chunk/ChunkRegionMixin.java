package com.dfsek.terra.fabric.mixin.implementations.chunk;

import com.dfsek.terra.api.block.BlockData;
import com.dfsek.terra.api.world.Chunk;
import com.dfsek.terra.api.world.World;
import com.dfsek.terra.fabric.block.FabricBlockData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkRegion;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChunkRegion.class)
@Implements(@Interface(iface = Chunk.class, prefix = "terra$", remap = Interface.Remap.NONE))
public abstract class ChunkRegionMixin {
    @Final
    @Shadow
    private ChunkPos centerPos;

    public int terra$getX() {
        return centerPos.x;
    }

    public int terra$getZ() {
        return centerPos.z;
    }

    public World terra$getWorld() {
        return (World) this;
    }

    public @NotNull BlockData terra$getBlockData(int x, int y, int z) {
        return new FabricBlockData(((ChunkRegion) (Object) this).getBlockState(new BlockPos(x + (centerPos.x << 4), y, z + (centerPos.z << 4))));
    }

    public void terra$setBlockData(int x, int y, int z, @NotNull BlockData blockData, boolean physics) {
        ((ChunkRegion) (Object) this).setBlockState(new BlockPos(x + (centerPos.x << 4), y, z + (centerPos.z << 4)), ((FabricBlockData) blockData).getHandle(), 0);
    }

    // getHandle already added in world/ChunkRegionMixin.
}
