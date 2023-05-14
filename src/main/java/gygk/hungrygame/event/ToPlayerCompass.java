package gygk.hungrygame.event;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToPlayerCompass implements Listener {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    private String uri;
    @EventHandler
    public void PlayerJoinGame(PlayerInteractEvent event){
        uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> livingPlayer = gameMap.getLivingPlayer();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < livingPlayer.size(); i++) {
            players.add(Bukkit.getPlayer(livingPlayer.get(i)));
        }

        ItemStack itemStack= event.getItem();
        Player player = event.getPlayer();
        String minPlayer = "";
        int min = 999;
        if(!Objects.isNull(itemStack)  && itemStack.getType() == Material.COMPASS){
            CompassMeta itemMeta = (CompassMeta) itemStack.getItemMeta();


            for (Player p: players) {

                if(player != p){
                    if(getDistance(player,p) < min){
                        min = getDistance(player,p);
                        minPlayer = p.getName();
                    }
                }


            }
            Player minP = Bukkit.getPlayer(minPlayer);
            itemMeta.setLodestone(minP.getLocation());
            itemMeta.setLodestoneTracked(false);
            itemStack.setItemMeta(itemMeta);
            player.sendMessage(ChatColor.GOLD + "最近玩家距离你" + min +"米");

//            ItemStack itemStack1 = new ItemStack(Material.COMPASS);
//            CompassMeta itemMeta1 = (CompassMeta) itemStack1.getItemMeta();
//            itemMeta1.setLodestone(minP.getLocation());
//            itemStack1.setItemMeta(itemMeta1);
//            PlayerInventory inventory = player.getInventory();
//            inventory.addItem(itemStack1);

        }


    }

    private int getDistance(Player player, Player p) {
        double x = player.getLocation().getX() - p.getLocation().getX();
        double y = player.getLocation().getY() - p.getLocation().getY();
        double z = player.getLocation().getZ() - p.getLocation().getZ();

        return (int)(Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)));
    }
}
