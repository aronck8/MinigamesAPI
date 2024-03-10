package com.aronck.minigamesapi.devtools;

import com.aronck.minigamesapi.InternalDataBridgeKey;
import com.aronck.minigamesapi.Main;
import com.aronck.minigamesapi.devtools.GUI.MainConfigGUI;
import com.aronck.minigamesapi.devtools.commands.ConfigCommand;
import com.aronck.minigamesapi.devtools.commands.DevMode;
import com.aronck.minigamesapi.devtools.commands.MinigamesCommand;
import com.aronck.minigamesapi.devtools.listeners.InventoryClickListener;
import com.aronck.minigamesapi.minigame.InternalDataFetcher;
import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.minigame.Minigames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class DToolController extends JavaPlugin {

    private static DToolController instance;

    public static final String DEBUG_MENU_NAME = "§cDEBUG";
    public static final String DEBUG_INFOS_MENU_NAME = "§cDEBUG Infos";
    public static final String DEBUG_TEAMS_MENU_NAME = "§cDEBUG Teams";

    public static final String USE_DEV_MODE_PERMISSION = "minigamesapi.debug,devtool";

    public boolean showDevMode = false;

    private Main main;
    private InternalDataBridgeKey key;
    private InternalDataFetcher dataFetcher;
    private DataFetchers dataFetchers;
    private MainConfigGUI mainConfigGUI;

    private HashMap<Player, Minigame> selectedMinigames = new HashMap<>();

    private Minigame currentMinigame;

    @Override
    public void onEnable() {
        super.onEnable();
        this.main = Main.getInstance();
        try {
            this.key = main.getKey();
            dataFetcher = new InternalDataFetcher(key);
            dataFetchers = new DataFetchers(dataFetcher);
            getCommand("devmode").setExecutor(new DevMode(this));
            getCommand("config").setExecutor(new ConfigCommand(this, dataFetchers));
            getCommand("minigameDebug").setExecutor(new MinigamesCommand(this));
            Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static DToolController getInstance() {
        return instance;
    }

    public Minigame getCurrentMinigame() {
        if(currentMinigame==null)currentMinigame=Minigames.get(0);
        return currentMinigame;
    }

    public void setCurrentMinigame(Minigame currentMinigame) {
        this.currentMinigame = currentMinigame;
    }

    public MainConfigGUI getMainConfigGUI() {
        return mainConfigGUI;
    }

    public void setMainConfigGUI(MainConfigGUI mainConfigGUI) {
        this.mainConfigGUI = mainConfigGUI;
    }

    public void setMinigameOfPlayer(Player player, Minigame minigame){
        selectedMinigames.put(player, minigame);
    }

    public Minigame getMinigameOfPlayer(Player player){
        selectedMinigames.computeIfAbsent(player, k -> Minigames.get(0));
        return selectedMinigames.get(player);
    }

    public boolean isShowDevMode() {
        return showDevMode;
    }

    public void setShowDevMode(boolean showDevMode) {
        this.showDevMode = showDevMode;
    }
}
