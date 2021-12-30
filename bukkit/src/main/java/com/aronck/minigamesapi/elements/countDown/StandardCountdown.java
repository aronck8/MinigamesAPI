package com.aronck.minigamesapi.elements.countDown;

import com.aronck.minigamesapi.minigame.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StandardCountdown extends Countdown {

    private StringBuffer countdownStringBuffer = new StringBuffer();

    private String prefix = "";

    private boolean useXPBar = true;

    public StandardCountdown(int duration, String prefix) {
        super(20, duration);
        this.prefix = prefix;
    }

    public StandardCountdown(int duration, String prefix, boolean useXPBar) {
        super(20, duration);
        this.prefix = prefix;
        this.useXPBar = useXPBar;
    }

    public StandardCountdown(int duration) {
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
    protected void tick(int seconds) {
        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(getTickString(seconds));
            if(useXPBar)player.setLevel(seconds);
        }
    }

    @Override
    protected void firstTick(int duration) {
        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage("§aDer Countdown wurde gestartet!");
            player.setLevel(duration);
            player.setExp(0);
        }
    }

    @Override
    protected void lastTick(int duration) {
        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage("§aDas Spiel startet jetzt!");
            player.setLevel(0);
            player.setExp(0);
        }
    }

}