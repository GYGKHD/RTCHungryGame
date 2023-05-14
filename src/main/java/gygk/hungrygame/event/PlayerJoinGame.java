package gygk.hungrygame.event;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

import java.util.List;
import java.util.Objects;

public class PlayerJoinGame implements Listener {

    @EventHandler
    public void PlayerJoinGame(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.GOLD + "温馨提示：使用/game join加入等待队列,使用/game leave离开等待队列");
        if(!player.isOp()){
            player.setGameMode(GameMode.SPECTATOR);
        }

    }
}
