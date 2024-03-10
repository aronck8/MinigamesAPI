package com.aronck.minigamesapi.devtools;

import com.aronck.minigamesapi.minigame.InternalDataFetcher;

public final class DataFetchers {
    private InternalDataFetcher minigameDataFetcher;

    DataFetchers(InternalDataFetcher minigameDataFetcher){
        this.minigameDataFetcher = minigameDataFetcher;
    }

    public InternalDataFetcher getMinigameDataFetcher() {
        return minigameDataFetcher;
    }
}
