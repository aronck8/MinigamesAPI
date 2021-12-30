package com.aronck.minigamesapi.commands.internalCommands;

import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.minigame.Minigames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MinigameSetupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 2 && args[0].equalsIgnoreCase("select")) {

            if (!(sender instanceof Player)) {
                System.out.println("Du musst ein Spieler sein!");
                return true;
            }

            Player player = (Player) sender;

            if(!player.hasPermission("minigame.setup")){
                player.sendMessage("§cDu hast keine Berechtigung dazu!");
                return true;
            }

            try {
                int minigameId = Integer.parseInt(args[1]);
                if(Minigames.getAmountOfRegisteredMinigames()-1<minigameId || Minigames.get(minigameId)==null){
                    player.sendMessage("§cEin Minigame mit der ID konnte nicht gefunden werden!");
                    return true;
                }
                Minigames.selectMinigame(player, minigameId);
                player.sendMessage("§aDer Spielmodus Nummer " + minigameId + " §awurde ausgewählt und wird jetzt editiert");
            }catch (NumberFormatException e){
                player.sendMessage("§c\"" + args[1] + "\" ist keine valide Zahl!");
                return true;
            }

        }
        return false;
    }

}
