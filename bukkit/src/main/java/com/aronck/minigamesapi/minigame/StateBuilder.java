package com.aronck.minigamesapi.minigame;

public abstract class StateBuilder<T extends AbstractState> {

    public abstract T build();

}
