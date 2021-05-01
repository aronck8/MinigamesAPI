package com.aronck.minigamesapi.elements.countDown;

import com.aronck.minigamesapi.minigame.CountDown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StandartCountDown extends CountDown {

    private StringBuffer countdownStringBuffer = new StringBuffer();

    private String prefix = "";

    public StandartCountDown(int duration, String prefix) {
        super(20, duration);
        this.prefix = prefix;
    }

    public StandartCountDown(int duration) {
        super(20, duration);
    }

    private String getTickString(int duration){
        int minutes = duration/60;
        int seconds = duration%60;
        countdownStringBuffer.append("§aDas Spiel startet in ");
        if(minutes!=0) countdownStringBuffer.append("§c").append(minutes).append(" §aMinuten ");
        if(minutes!=0 && seconds!=0)countdownStringBuffer.append("§aund ");
        if(seconds!=0) countdownStringBuffer.append("§c").append(seconds).append(" §aSekunde(n)");
        String countdownString = countdownStringBuffer.toString();
        countdownStringBuffer.delete(0, countdownStringBuffer.length());
        return countdownString;
    }

    @Override
    public void tick(int seconds) {

        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(getTickString(seconds));
            player.setLevel(seconds);
        }
    }

    @Override
    public void firstTick() {
        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage("§aDer Countdown wurde gestartet!");
        }
    }

    @Override
    public void lastTick() {
        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage("§aDas Spiel startet jetzt!");
        }
    }
}