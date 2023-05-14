package gygk.hungrygame.task;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Warning;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DuelTask extends BukkitRunnable {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    private Server server;
    private World world;
    private Player player;
    private String uri;

    public DuelTask(Server server, World world, Player player) {
        this.server = server;
        this.world = world;
        this.player = player;
    }

    @Override
    public void run() {
        world.setPVP(false);

        for (int i = 5; i > 0; i--) {
            server.broadcastMessage(i + "s后进入死斗模式");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration config1 = this.config.getConfig();
        config1.set("duel",1);

        for (int i = 5; i > 0; i--) {
            server.broadcastMessage(i + "s后开启PVP");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        world.setPVP(true);
    }
}
