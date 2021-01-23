package tests;

import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;

public class Test {

    public Test(String s){

    }

    @EventHandler
    public void stuff(){

    }

    private int variable = 10;
    @Deprecated
    public void print(){
        System.out.println(variable);
    }

    @Nonnull
    public String toString() {
        return "Test{" +
                "variable=" + variable +
                '}';
    }
}
