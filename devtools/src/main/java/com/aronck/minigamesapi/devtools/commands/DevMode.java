package com.aronck.minigamesapi.devtools.commands;

import com.aronck.minigamesapi.devtools.DToolController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DevMode implements CommandExecutor {

    private DToolController main;

    public DevMode(DToolController main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission(DToolController.USE_DEV_MODE_PERMISSION))return true;

            if(args.length==1){
                if(args[0].equalsIgnoreCase("show")){
                    main.setShowDevMode(true);
                    return true;
                }else if(args[0].equalsIgnoreCase("hide")){
                    main.setShowDevMode(false);
                    return true;
                }
            }

        }

        return false;
    }
}
