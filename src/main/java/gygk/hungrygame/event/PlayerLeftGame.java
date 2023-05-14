package gygk.hungrygame.event;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class PlayerLeftGame implements Listener {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    @EventHandler
    public void PlayerRemoveFromGame(PlayerQuitEvent event){
        String uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
        Player player = event.getPlayer();
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> players = gameMap.getPlayer();
        List<String> livingPlayers = gameMap.getLivingPlayer();
        List<String> diePlayer = gameMap.getDiePlayer();
        for (int i = 0; i < players.size(); i++) {
            String[] split = players.get(i).split("/");
            if(split[0].equals(player.getName())){
                players.remove(i);
                break;
            }
        }
        if(gameMap.getStatus() == 1){
            for (int i = 0; i < livingPlayers.size(); i++) {
                String[] split = livingPlayers.get(i).split("/");
                if(split[0].equals(player.getName())){
                    livingPlayers.remove(i);
                    diePlayer.add(split[0]);
                    player.getWorld().strikeLightningEffect(player.getLocation());
                    player.setHealth(0);
                    event.setQuitMessage(ChatColor.YELLOW +player.getName() + "临阵脱逃");
                    break;
                }
            }
        }

        gameMap.setPlayer(players);
        gameMap.setLivingPlayer(livingPlayers);
        gameMap.setDiePlayer(diePlayer);
        FileUtil.writeGameMapFiles(uri,gameMap);
    }
}
