package com.hakimen.hex_machina.common.utils.registration;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Recorder<T>{
    private final String modId;
    private final Registry<T> registry;
    private final List<T> holder;

    public Recorder(Registry<T> registry, String modId) {
        this.modId = modId;
        this.registry = registry;
        this.holder = new ArrayList<>();
    }

    public <X extends T> Supplier<X> register(String name, Supplier<X> object){
        X temp = Registry.register(registry,new ResourceLocation(modId, name), object.get());
        holder.add(temp);
        return () -> temp;
    }
    public Registry<T> getRegistry(){
        return registry;
    }

    public List<T> getHolder() {
        return holder;
    }
}
