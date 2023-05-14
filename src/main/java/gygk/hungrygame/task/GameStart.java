package gygk.hungrygame.task;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class GameStart extends BukkitRunnable {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    private Player player;

    public GameStart(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        String uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
        for (int i = 5; i >= 0; i--) {
            for (Player player: Bukkit.getOnlinePlayers()) {
                player.sendTitle("游戏将在" + i+"秒后开始","请做好准备");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        new GameIni(player).runTask(config);



    }
}
