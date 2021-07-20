package com.dfsek.terra.addons.noise.config;

import com.dfsek.tectonic.annotations.Value;
import com.dfsek.tectonic.loading.object.ObjectTemplate;
import com.dfsek.terra.api.noise.NoiseSampler;

public class DimensionApplicableNoiseSampler implements ObjectTemplate<DimensionApplicableNoiseSampler> {
    @Value("dimensions")
    private int dimensions;

    @Value(".")
    private NoiseSampler sampler;

    public int getDimensions() {
        return dimensions;
    }

    public NoiseSampler getSampler() {
        return sampler;
    }

    @Override
    public DimensionApplicableNoiseSampler get() {
        return this;
    }
}
