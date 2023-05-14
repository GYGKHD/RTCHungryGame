package gygk.hungrygame.event;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class PlayerDie implements Listener {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    @EventHandler
    public void PlayerKillPlayer(EntityDeathEvent event){
        String uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> playPlayer = gameMap.getPlayPlayer();
        List<String> livingPlayer = gameMap.getLivingPlayer();
        List<String> diePlayer = gameMap.getDiePlayer();
        if(gameMap.getStatus() == 1 ){
            if(event.getEntity() instanceof Player && event.getEntity().getKiller() instanceof Player){
                Player killer = event.getEntity().getKiller().getPlayer();
                Player player = (Player) event.getEntity();
                for (int i = 0; i < playPlayer.size(); i++) {
                    String[] split = playPlayer.get(i).split("/");
                    int kill = Integer.parseInt(split[1]);
                    if(split[0].equals(killer.getName())){
                        kill++;
                        String newdata = killer.getName()+"/"+kill;
                        playPlayer.remove(i);
                        playPlayer.add(newdata);
                        break;
                    }
                }
                for (int i = 0; i < livingPlayer.size(); i++) {
                    if(livingPlayer.get(i).equals(player.getName())){
                        diePlayer.add(livingPlayer.get(i));
                        livingPlayer.remove(i);
                    }
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.getWorld().strikeLightningEffect(player.getLocation());
                FileUtil.writeGameMapFiles(uri,gameMap);
                return;
            }
            if(event.getEntity() instanceof Player){
                Player player = (Player) event.getEntity();
                for (int i = 0; i < livingPlayer.size(); i++) {
                    if(livingPlayer.get(i).equals(player.getName())){
                        diePlayer.add(livingPlayer.get(i));
                        livingPlayer.remove(i);
                    }
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.getWorld().strikeLightningEffect(player.getLocation());
                FileUtil.writeGameMapFiles(uri,gameMap);
            }
        }

    }
}
