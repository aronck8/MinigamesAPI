package com.aronck.minigamesapi.commands.internalCommands;

import com.aronck.minigamesapi.elements.locations.Locations;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationChooserCommand implements CommandExecutor {

    // /locations set <name>
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length==2&&args[0].equalsIgnoreCase("set")){
            if(!(sender instanceof Player)){
                System.out.println("Du musst ein Spieler sein!");
                return true;
            }

            Player player = (Player) sender;

            if(!player.hasPermission("minigame.setLocation")){
                player.sendMessage("§cDu hast keine Berechtigung dazu!");
                return true;
            }

            player.sendMessage("Die Position für: " + args[1] + " wurde gesetzt!");
            Locations.saveLocation(args[1], player.getLocation());
        }

        return false;
    }
}
