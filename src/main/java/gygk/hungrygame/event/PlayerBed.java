package gygk.hungrygame.event;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerBed implements Listener {
    @EventHandler
    public void PlayerBedEnterEvent(PlayerBedEnterEvent event){
        Player player = event.getPlayer();
        player.getServer().broadcastMessage(ChatColor.GOLD + player.getName() + "都准备睡觉了,没人管管吗?");

        if(player.getHealth() >=2) {
            player.setHealth(player.getHealth()-2);
        } else {
            player.setHealth(0);
        }
        player.sendMessage("你花了一点血来嘲讽");
        player.playSound(player, Sound.ENTITY_PLAYER_HURT,1f,1f);
    }
}
