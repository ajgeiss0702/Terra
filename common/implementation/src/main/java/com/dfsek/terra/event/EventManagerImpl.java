package com.dfsek.terra.event;

import java.util.HashMap;
import java.util.Map;

import com.dfsek.terra.api.Platform;
import com.dfsek.terra.api.event.EventHandler;
import com.dfsek.terra.api.event.EventManager;
import com.dfsek.terra.api.event.events.Event;
import com.dfsek.terra.api.event.functional.FunctionalEventHandler;


public class EventManagerImpl implements EventManager {
    private final Map<Class<?>, EventHandler> handlers = new HashMap<>();
    private final Platform platform;
    
    public EventManagerImpl(Platform platform) {
        this.platform = platform;
        registerHandler(FunctionalEventHandler.class, new FunctionalEventHandlerImpl(platform)); // default handler
    }
    
    @Override
    public void callEvent(Event event) {
        handlers.values().forEach(handler -> handler.handle(event));
    }
    
    @Override
    public <T extends EventHandler> void registerHandler(Class<T> clazz, T handler) {
        handlers.put(clazz, handler);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends EventHandler> T getHandler(Class<T> clazz) {
        return (T) handlers.computeIfAbsent(clazz, c -> {
            throw new IllegalArgumentException("No event handler registered for class " + clazz.getCanonicalName());
        });
    }
}
