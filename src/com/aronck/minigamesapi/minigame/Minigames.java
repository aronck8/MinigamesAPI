package com.aronck.minigamesapi.minigame;

import java.util.ArrayList;

public final class Minigames {

    private static final ArrayList<Minigame> minigames = new ArrayList<>();

    private Minigames() {}

    static void addMinigame(Minigame minigame) {
        minigames.add(minigame);
    }

    static int getNextId() {
        if (minigames.isEmpty()) return 0;
        return minigames.get(minigames.size() - 1).getID() + 1;
    }

    public static Minigame get(int index) {
        return minigames.get(index);
    }

}
