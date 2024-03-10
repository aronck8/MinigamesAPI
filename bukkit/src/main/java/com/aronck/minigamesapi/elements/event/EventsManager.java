package com.aronck.minigamesapi.elements.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventsManager implements Listener {

    boolean debug = false;

    private JavaPlugin main;

    private HashMap<Class<? extends Event>, List<IEvent<? extends Event>>> events = new HashMap<>();

    public EventsManager(JavaPlugin main){
        this.main = main;
    }

    public <T extends Event> void addEvent(Class<T> clazz, IEvent<T> event){
        events.computeIfAbsent(clazz, k -> new ArrayList<>());
        events.get(clazz).add(event);
    }

    public HashMap<Class<? extends Event>, List<IEvent<? extends Event>>> getEvents() {
        return events;
    }

    public void registerListeners() {
        //Bukkit.getPluginManager().registerEvents(new LegacyEventCaller(this), main);
    }

    public void unregisterEvents(){

    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }
}
