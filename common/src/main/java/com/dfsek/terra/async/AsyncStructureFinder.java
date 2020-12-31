package com.dfsek.terra.async;

import com.dfsek.terra.api.math.MathUtil;
import com.dfsek.terra.api.math.vector.Location;
import com.dfsek.terra.api.math.vector.Vector3;
import com.dfsek.terra.api.platform.TerraPlugin;
import com.dfsek.terra.api.structures.structure.Rotation;
import com.dfsek.terra.api.util.FastRandom;
import com.dfsek.terra.biome.UserDefinedBiome;
import com.dfsek.terra.biome.grid.master.TerraBiomeGrid;
import com.dfsek.terra.generation.items.TerraStructure;
import net.jafama.FastMath;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Consumer;

public class AsyncStructureFinder extends AsyncFeatureFinder<TerraStructure> {
    public AsyncStructureFinder(TerraBiomeGrid grid, TerraStructure target, @NotNull Location origin, int startRadius, int maxRadius, Consumer<Vector3> callback, TerraPlugin main) {
        super(grid, target, origin, startRadius, maxRadius, callback, main);
        setSearchSize(target.getSpawn().getWidth() + 2 * target.getSpawn().getSeparation());
    }

    @Override
    public Vector3 finalizeVector(Vector3 orig) {
        return target.getSpawn().getChunkSpawn(orig.getBlockX(), orig.getBlockZ(), world.getSeed());
    }

    @Override
    public boolean isValid(int x, int z, TerraStructure target) {
        Location spawn = target.getSpawn().getChunkSpawn(x, z, world.getSeed()).toLocation(world);
        if(!((UserDefinedBiome) grid.getBiome(spawn)).getConfig().getStructures().contains(target)) return false;
        Random random = new FastRandom(MathUtil.getCarverChunkSeed(FastMath.floorDiv(spawn.getBlockX(), 16), FastMath.floorDiv(spawn.getBlockZ(), 16), world.getSeed()));
        return target.getStructure().get(random).test(spawn.setY(target.getSpawnStart().get(random)), random, Rotation.fromDegrees(90 * random.nextInt(4)));
    }
}