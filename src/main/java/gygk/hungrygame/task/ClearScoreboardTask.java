package gygk.hungrygame.task;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClearScoreboardTask extends BukkitRunnable {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    private String uri;
//    private BukkitTask bukkitTask;
//
//    public ClearScoreboardTask(BukkitTask bukkitTask) {
//        this.bukkitTask = bukkitTask;
//    }

    @Override
    public void run() {
//        bukkitTask.cancel();
            for (Player player: Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = player.getScoreboard();


                scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

                player.setScoreboard(scoreboard);

            }




    }
}
