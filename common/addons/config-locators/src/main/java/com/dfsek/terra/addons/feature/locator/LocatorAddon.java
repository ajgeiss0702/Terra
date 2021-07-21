package com.dfsek.terra.addons.feature.locator;

import com.dfsek.tectonic.loading.object.ObjectTemplate;
import com.dfsek.terra.addons.feature.locator.config.RandomLocatorTemplate;
import com.dfsek.terra.addons.feature.locator.config.SurfaceLocatorTemplate;
import com.dfsek.terra.api.TerraPlugin;
import com.dfsek.terra.api.addon.TerraAddon;
import com.dfsek.terra.api.addon.annotations.Addon;
import com.dfsek.terra.api.addon.annotations.Author;
import com.dfsek.terra.api.addon.annotations.Version;
import com.dfsek.terra.api.event.EventListener;
import com.dfsek.terra.api.event.events.config.pack.ConfigPackPreLoadEvent;
import com.dfsek.terra.api.injection.annotations.Inject;
import com.dfsek.terra.api.registry.CheckedRegistry;
import com.dfsek.terra.api.structure.feature.Locator;
import com.dfsek.terra.api.util.reflection.TypeKey;

import java.util.function.Supplier;

@Addon("config-locators")
@Version("1.0.0")
@Author("Terra")
public class LocatorAddon extends TerraAddon implements EventListener {

    public static final TypeKey<Supplier<ObjectTemplate<Locator>>> LOCATOR_TOKEN = new TypeKey<>() {};
    @Inject
    private TerraPlugin main;

    @Override
    public void initialize() {
        main.getEventManager().registerListener(this, this);
    }

    public void onPackLoad(ConfigPackPreLoadEvent event) {
        CheckedRegistry<Supplier<ObjectTemplate<Locator>>> locatorRegistry = event.getPack().getOrCreateRegistry(LOCATOR_TOKEN);
        locatorRegistry.register("SURFACE", () -> new SurfaceLocatorTemplate(main));
        locatorRegistry.register("RANDOM", RandomLocatorTemplate::new);
    }
}