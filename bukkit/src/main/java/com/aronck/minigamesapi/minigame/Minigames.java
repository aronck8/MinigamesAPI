package com.aronck.minigamesapi.minigame;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class Minigames {

    private static final ArrayList<Minigame> minigames = new ArrayList<>();
    private static final HashMap<Player, Minigame> selectedMinigames = new HashMap<>(); //list to save which minigame a player doing the setup has selected

    private Minigames() {}

    static void addMinigame(Minigame minigame) {
        minigames.add(minigame);
    }

    static int getNextId() {
        //if (minigames.isEmpty()) return 0;
        //return minigames.get(minigames.size() - 1).getID() + 1;
        return minigames.size();
    }

    public static int getAmountOfRegisteredMinigames(){
        return minigames.size();
    }

    public static Minigame get(int index) {
        return minigames.get(index);
    }

    public static List<Minigame> getMinigames() {
        return Collections.unmodifiableList(minigames);
    }

    public static void selectMinigame(Player player, Minigame minigame){
        selectedMinigames.put(player, minigame);
    }

    public static void selectMinigame(Player player, int minigameId){
        selectedMinigames.put(player, get(minigameId));
    }

    public static Minigame getCurrentlySelectedMinigame(Player player){
        selectedMinigames.putIfAbsent(player, get(0));
        return selectedMinigames.get(player);
    }

}
