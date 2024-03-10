package com.aronck.minigamesapi.devtools.commands;

import com.aronck.minigamesapi.devtools.DToolController;
import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.minigame.Minigames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MinigamesCommand implements CommandExecutor {

    private DToolController main;

    public MinigamesCommand(DToolController main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {

            Player player = (Player) sender;
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("select")) {
                    Minigame selectedMinigame = Minigames.get(Integer.parseInt(args[1]));
                    main.setMinigameOfPlayer(player, selectedMinigame);
                    player.sendMessage("§2Es wurde erfolgreich das Minigage §f" + selectedMinigame.getName() + " §2ausgewählt");
                    return true;
                }
            }
        }

        return false;
    }


}
