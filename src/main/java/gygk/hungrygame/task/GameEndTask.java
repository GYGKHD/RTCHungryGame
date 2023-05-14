package gygk.hungrygame.task;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class GameEndTask extends BukkitRunnable {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);

    private List<BukkitTask> bukkitTasks = new ArrayList<>();


    public GameEndTask(List<BukkitTask> bukkitTasks) {
        this.bukkitTasks = bukkitTasks;
    }

    @Override
    public void run() {
        String uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        if(gameMap.getStatus() == 0){
            for (int i = 0; i <bukkitTasks.size(); i++) {
                bukkitTasks.get(i).cancel();

            }
            ClearScoreboardTask clearScoreboardTask = new ClearScoreboardTask();
            clearScoreboardTask.runTask(config);
            cancel();
        }


    }
}
