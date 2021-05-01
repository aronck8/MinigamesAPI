package com.aronck.minigamesapi.elements.event;

public class Event<T extends org.bukkit.event.Event>{

	private Class<T> clazz;
	private IEvent<T> action;

	public Event(Class<T> clazz, IEvent<T> action) {
		this.clazz = clazz;
		this.action = action;
	}

	public IEvent<T> getAction(){
		return action;
	}

	public Class<T> getClazz(){
		return clazz;
	}
}