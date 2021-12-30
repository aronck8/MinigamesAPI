package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.events.custom.MinigameStartEvent;
import org.bukkit.Bukkit;

public abstract class Countdown {

    protected Minigame minigame;

    protected long timerStep;
    protected int taskId;

    protected int duration = 0;
    private static final int STANDARD_DURATION = 30;
    protected int currentContdownTime = 0;

    public Countdown(long timerStep, int duration) {
        this.timerStep = timerStep;
        this.duration = duration;
    }

    void setMinigame(Minigame minigame){
        this.minigame = minigame;
    }

    public void start(){
        start(duration == 0 ? STANDARD_DURATION : duration);
    }

    public void start(int time){
        firstTick(time);
        currentContdownTime = time;
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(minigame.main, new Runnable() {
            int current = time;
            @Override
            public void run() {

                if(current>=1)tick(current);
                else if(current==0) lastTick(currentContdownTime);
                else stop();
                current--;
            }
        }, 0, timerStep);
    }

    protected void firstTick(int duration){}

    protected abstract void tick(int numberOfStep);

    protected void lastTick(int duration){}

    protected void performPostStopActions(int duration){}

    public void stop(){
        if(taskId!=0) Bukkit.getScheduler().cancelTask(taskId);
        MinigameStartEvent minigameStartEvent = new MinigameStartEvent(minigame);
        Bukkit.getPluginManager().callEvent(minigameStartEvent);
        if(!minigameStartEvent.isCancelled())minigame.startMiniGame();
        performPostStopActions(currentContdownTime);
    }

}
