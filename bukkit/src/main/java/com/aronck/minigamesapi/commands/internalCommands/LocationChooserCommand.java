package com.aronck.minigamesapi.commands.internalCommands;

import com.aronck.minigamesapi.elements.locations.LocationChooser;
import com.aronck.minigamesapi.elements.locations.Locations;
import com.aronck.minigamesapi.minigame.Minigames;
import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            List<String> locationKeys = new ArrayList<>();
            for (LocationChooser locationChooser : Minigames.getCurrentlySelectedMinigame(player).getLocationChoosers()) {
                String s = locationChooser.getKey();
                locationKeys.add(s);
                if (s.equals(args[1])) {
                    player.sendMessage("Die Position für: \"" + args[1] + "\" wurde gesetzt!");
                    Locations.saveLocation(args[1], player.getLocation());
                    return true;
                }
            }

            player.sendMessage("Es konnte keine Location mit dem Namen gefunden werden. ");
            player.sendMessage("Die registrierten Positionen sind: " + Arrays.toString(locationKeys.toArray()) + "!");

        }

        return false;
    }
}
