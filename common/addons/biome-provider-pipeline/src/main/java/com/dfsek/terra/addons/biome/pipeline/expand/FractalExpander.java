package com.dfsek.terra.addons.biome.pipeline.expand;

import com.dfsek.terra.addons.biome.pipeline.api.BiomeExpander;
import com.dfsek.terra.api.noise.NoiseSampler;
import com.dfsek.terra.api.util.MathUtil;
import com.dfsek.terra.api.world.biome.TerraBiome;

public class FractalExpander implements BiomeExpander {
    private final NoiseSampler sampler;

    public FractalExpander(NoiseSampler sampler) {
        this.sampler = sampler;
    }

    @Override
    public TerraBiome getBetween(double x, double z, long seed, TerraBiome... others) {
        return others[MathUtil.normalizeIndex(sampler.getNoiseSeeded(seed, x, z), others.length)];
    }
}
