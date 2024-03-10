package com.aronck.minigamesapi.devtools.commands;

import com.aronck.minigamesapi.devtools.DToolController;
import com.aronck.minigamesapi.devtools.DataFetchers;
import com.aronck.minigamesapi.devtools.GUI.MainConfigGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigCommand implements CommandExecutor {

    private DToolController main;
    private DataFetchers dataFetchers;

    public ConfigCommand(DToolController main, DataFetchers dataFetchers) {
        this.main = main;
        this.dataFetchers = dataFetchers;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            main.setMainConfigGUI(new MainConfigGUI(main, main.getMinigameOfPlayer(player), dataFetchers));
            main.getMainConfigGUI().openInventory(player);
        }
        return false;
    }
}
