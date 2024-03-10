package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.Main;
import com.aronck.minigamesapi.elements.map.GameMap;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MinigameBuilder{

    private Minigame minigame;

    public MinigameBuilder(JavaPlugin plugin) {
        this.minigame = new Minigame(plugin);
    }

    /*public MinigameBuilder addEvent(Event event) {
        minigame.eventsManager.addEvent(event.getClazz(), event.getAction());
        return this;
    }

    public <T extends org.bukkit.event.Event> MinigameBuilder addEvent(Class<T> clazz, IEvent<T> event){
        minigame.eventsManager.addEvent(clazz, event);
        return this;
    }*/

    public MinigameBuilder addMap(GameMap map) {
        minigame.maps.add(map);
        if(minigame.currentMap==null)minigame.currentMap = map;
        return this;
    }

    public MinigameBuilder setLocationChooserItem(ItemStack item) {
        minigame.locationChooserItem = item;
        return this;
    }

    public MinigameBuilder setTeamsChooserItem(ItemStack item) {
        minigame.teamChooserItem = item;
        return this;
    }

    public MinigameBuilder setCountdown(Countdown countdown) {
        minigame.countDown = countdown;
        return this;
    }

    public MinigameBuilder addMinigameState(AbstractState state){
        state.minigame = minigame;
        if(minigame.currentState==null)minigame.currentState=state;
        minigame.lastState.setNextState(state);
        minigame.lastState = state;
        return this;
    }

    public Minigame build() {
        minigame.preInitGameStates();
        return minigame;
    }

}
