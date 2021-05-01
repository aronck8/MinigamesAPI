package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.events.custom.MinigameStartEvent;
import org.bukkit.Bukkit;

public abstract class CountDown {

    protected Minigame minigame;

    protected long timerStep;
    protected int taskId;

    protected final int duration;

    public CountDown(long timerStep, int duration) {
        this.timerStep = timerStep;
        this.duration = duration;
    }

    final void start0(Minigame minigame){
        this.minigame = minigame;
        firstTick();
        start();

        Bukkit.getScheduler().scheduleSyncDelayedTask(minigame.main, () -> {
            lastTick();
            MinigameStartEvent minigameStartEvent = new MinigameStartEvent(minigame);
            Bukkit.getPluginManager().callEvent(minigameStartEvent);
            if(!minigameStartEvent.isCancelled())minigame.startMiniGame();
        },timerStep * duration + 1);

    }

    public void firstTick(){}

    public void start(){
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(minigame.main, new Runnable() {
            int current = duration;
            @Override
            public void run() {
                tick(current);

                if(current>1)current--;
                else stop();
            }
        }, 0, timerStep);
    }

    public abstract void tick(int numberOfStep);

    public void lastTick(){}

    public void stop(){
        if(taskId!=0) Bukkit.getScheduler().cancelTask(taskId);
    }

}
