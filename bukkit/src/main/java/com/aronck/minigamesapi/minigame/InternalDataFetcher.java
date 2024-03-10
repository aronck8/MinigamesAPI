package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.InternalDataBridgeKey;

import java.util.ArrayList;
import java.util.List;

public final class InternalDataFetcher {

    private final InternalDataBridgeKey key;

    public InternalDataFetcher(InternalDataBridgeKey key) throws IllegalAccessException {
        this.key = key;
        ensureValidated();
    }

    private void ensureValidated() throws IllegalAccessException {
        if(!key.isValidated()) throw new IllegalAccessException("Unallowed external access!");
    }

    public AbstractState getFirstState(Minigame minigame){
        if(!key.isValidated())return null;
        return minigame.firstState;
    }

    public List<AbstractState> getStates(Minigame minigame){
        if(!key.isValidated())return new ArrayList<>();
        List<AbstractState> states = new ArrayList<>();

        AbstractState currentState = minigame.firstState;
        states.add(currentState);
        while ((currentState = currentState.getNextState())!=null) states.add(currentState);

        return states;
    }

}
