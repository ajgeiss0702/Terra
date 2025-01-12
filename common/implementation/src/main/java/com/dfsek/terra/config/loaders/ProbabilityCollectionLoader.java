package com.dfsek.terra.config.loaders;

import com.dfsek.tectonic.exception.LoadException;
import com.dfsek.tectonic.loading.ConfigLoader;
import com.dfsek.tectonic.loading.TypeLoader;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.List;
import java.util.Map;

import com.dfsek.terra.api.util.collection.ProbabilityCollection;


@SuppressWarnings("unchecked")
public class ProbabilityCollectionLoader implements TypeLoader<ProbabilityCollection<Object>> {
    @Override
    public ProbabilityCollection<Object> load(AnnotatedType type, Object o, ConfigLoader configLoader) throws LoadException {
        ProbabilityCollection<Object> collection = new ProbabilityCollection<>();
        
        if(type instanceof AnnotatedParameterizedType) {
            AnnotatedParameterizedType pType = (AnnotatedParameterizedType) type;
            AnnotatedType generic = pType.getAnnotatedActualTypeArguments()[0];
            if(o instanceof Map) {
                Map<Object, Integer> map = (Map<Object, Integer>) o;
                for(Map.Entry<Object, Integer> entry : map.entrySet()) {
                    collection.add(configLoader.loadType(generic, entry.getKey()), entry.getValue());
                }
            } else if(o instanceof List) {
                List<Map<Object, Integer>> map = (List<Map<Object, Integer>>) o;
                for(Map<Object, Integer> l : map) {
                    for(Map.Entry<Object, Integer> entry : l.entrySet()) {
                        if(entry.getValue() == null) throw new LoadException("No probability defined for entry \"" + entry.getKey() + "\"");
                        Object val = configLoader.loadType(generic, entry.getKey());
                        collection.add(val, entry.getValue());
                    }
                }
            } else if(o instanceof String) {
                return new ProbabilityCollection.Singleton<>(configLoader.loadType(generic, o));
            } else {
                throw new LoadException("Malformed Probability Collection: " + o);
            }
        } else throw new LoadException("Unable to load config! Could not retrieve parameterized type: " + type);
        
        
        return collection;
    }
    
}
