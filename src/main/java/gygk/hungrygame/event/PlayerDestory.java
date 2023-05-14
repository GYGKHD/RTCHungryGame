package gygk.hungrygame.event;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerDestory implements Listener {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    private String uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
    @EventHandler
    public void PlayerDestory(BlockBreakEvent event){
        Player player = event.getPlayer();
        event.getBlock();
        if(!player.isOp()){
            event.setCancelled(true);
        }
        if(player.isOp() && !config.getConfig().getBoolean("adminblock")){
            event.setCancelled(true);
        }

    }
    @EventHandler
    public void PlayerBucket(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = new ItemStack(Material.DANDELION);
        item = player.getInventory().getItem(event.getNewSlot());
        if(!Objects.isNull(item)){
            if(!player.isOp() && item.getType() == Material.BUCKET){

                event.setCancelled(true);
                player.sendMessage("小伙子，桶是用不了的");
            }
        }


    }




    @EventHandler
    public void playerPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(!player.isOp()){
            event.setCancelled(true);
        }
    }




    }

