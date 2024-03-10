package com.aronck.minigamesapi.minigame;

import org.bukkit.Bukkit;
import com.aronck.minigamesapi.Main;

import java.util.Arrays;
import java.util.List;

public abstract class Countdown {

    protected Minigame minigame;
    private boolean started = false;
    protected long timerStep;
    protected int taskId = 0;

    protected int duration;
    private static final int STANDARD_DURATION = 30;
    protected int currentContdownTime;
    protected List<Integer> specialTimeStamps;

    public Countdown(long timerStep, int duration, Integer... specialTimeStamps) {
        this.timerStep = timerStep;
        this.duration = duration;
        currentContdownTime = duration;
        this.specialTimeStamps = Arrays.asList(specialTimeStamps);
    }

    public void start(Minigame minigame){
        start(minigame, duration == 0 ? STANDARD_DURATION : currentContdownTime);
    }

    public void start(Minigame minigame, int time){
        started = true;
        this.minigame = minigame;
        firstTick(minigame, time);
        currentContdownTime = time;
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            private int current = time;
            @Override
            public void run() {

                if(current<0)stop();
                if(isSpecial(current))specialTick(current);
                if(current>=1)tick(minigame, current);
                else if(current==0) lastTick(minigame, duration);
                else stop();
                current--;
                currentContdownTime = current;
            }
        }, 0, timerStep);
    }

    private boolean isSpecial(int numberOfStep){
        return specialTimeStamps.contains(numberOfStep);
    }

    protected void firstTick(Minigame minigame, int duration){}

    protected abstract void tick(Minigame minigame, int numberOfStep);

    protected void specialTick(int numberOfStep){}

    protected void lastTick(Minigame minigame, int duration){}

    protected void performPostStopActions(Minigame minigame, int duration){}

    public void cancel(){
        if(taskId!=0) Bukkit.getScheduler().cancelTask(taskId);
    }

    public void stop(){
        if(!started)return;
        started = false;
        if(taskId!=0) Bukkit.getScheduler().cancelTask(taskId);
        performPostStopActions(minigame, currentContdownTime);
    }

    public int getCurrentContdownTime() {
        return currentContdownTime;
    }
}
