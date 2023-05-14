package gygk.hungrygame;

import gygk.hungrygame.event.*;
import gygk.hungrygame.executor.ChangeAdminBlockExecutor;
import gygk.hungrygame.executor.JoinGame;
import gygk.hungrygame.executor.ManaItem;
import gygk.hungrygame.executor.ManaMap;
import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class HungryGame extends JavaPlugin {



    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(new PlayerLeftGame(),this);
        getServer().getPluginManager().registerEvents(new PlayerJoinGame(),this);
        getServer().getPluginManager().registerEvents(new PlayerDie(),this);
        getServer().getPluginManager().registerEvents(new PlayerDestory(),this);
        getServer().getPluginManager().registerEvents(new ToPlayerCompass(),this);
        getServer().getPluginManager().registerEvents(new EntityDamageBy(),this);
        getServer().getPluginManager().registerEvents(new PlayerBed(),this);
        System.out.println("HungryGame Started");

        getCommand("manaitem").setExecutor(new ManaItem());
        getCommand("game").setExecutor(new JoinGame());
        getCommand("manamap").setExecutor(new ManaMap());
        getCommand("adminblock").setExecutor(new ChangeAdminBlockExecutor());
        iniGameMap();
        saveDefaultConfig();
    }

    private void iniGameMap() {
        String uri = String.valueOf(getDataFolder()) + "\\GameMap.json";
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> lists = new ArrayList<>();
        gameMap.setPlayer(lists);
        FileUtil.writeGameMapFiles(uri,gameMap);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
