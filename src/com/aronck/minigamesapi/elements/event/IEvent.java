package com.aronck.minigamesapi.elements.event;


public interface IEvent<T extends org.bukkit.event.Event> {

    void run(T e);

}
