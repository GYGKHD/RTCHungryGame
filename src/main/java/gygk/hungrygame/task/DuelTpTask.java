package gygk.hungrygame.task;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DuelTpTask extends BukkitRunnable {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    private Server server;
    private World world;
    private String uri;

    public DuelTpTask(Server server, World world) {
        this.server = server;
        this.world = world;
    }

    @Override
    public void run() {
        uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
        if(config.getConfig().getInt("duel") == 1){
            GameMap gameMap = FileUtil.readGameMapFiles(uri);
            List<String> livingPlayer = gameMap.getLivingPlayer();
            for (Player player: Bukkit.getOnlinePlayers()) {
                Location location = new Location(world, 263, 63, 278);
                player.teleport(location);
            }
            cancel();
        }


    }
}
