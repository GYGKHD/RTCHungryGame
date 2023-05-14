package gygk.hungrygame.task;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckPlayer extends BukkitRunnable {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    private String uri;
    private Server server;


    public CheckPlayer(String uri, Server server) {
        this.uri = uri;
        this.server = server;
    }

    @Override
    public void run() {
        uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> livingPlayer = gameMap.getLivingPlayer();
        List<String> playPlayer = gameMap.getPlayPlayer();
        String killKingS = "";
        int kill = 0;
//        System.out.println(livingPlayer);
        if(livingPlayer.size() == 1){
            gameMap.setStatus(0);

            Player winPlayer = Bukkit.getPlayer(livingPlayer.get(0));
//            winPlayer.setGameMode(GameMode.SPECTATOR);/**/
//            List<String> playPlayer = gameMap.getPlayPlayer();
//            List<Integer> killKing = new ArrayList<>();
//            for (int i = 0; i < playPlayer.size(); i++) {
//                String[] split = playPlayer.get(i).split("/");
//                killKing.add(Integer.valueOf(split[1]));
//            }
//            Integer max = Collections.max(killKing);
            for (int i = 0; i < playPlayer.size(); i++) {
                String[] split = playPlayer.get(i).split("/");
                if(Integer.parseInt(split[1]) > kill){
                    kill = Integer.parseInt(split[1]);
                    killKingS = split[0];
                }
            }


            for (Player player: Bukkit.getOnlinePlayers()) {
                if (player != winPlayer){
                    player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH,0.5f,1f);
                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1f,1f);
                }
                player.sendTitle(livingPlayer.get(0)+"是新的捍卫者",killKingS + "是击杀王",0,60,20);
            }

            iniPlayer();

            config.getConfig().set("duel",0);
            cancel();
        }
    }

    private void iniPlayer() {
        List<String> ini = new ArrayList<>();
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        gameMap.setStatus(0);
        gameMap.setPlayPlayer(ini);
        gameMap.setDiePlayer(ini);
        gameMap.setLivingPlayer(ini);
        gameMap.setPlayer(ini);
        FileUtil.writeGameMapFiles(uri,gameMap);
    }
}
