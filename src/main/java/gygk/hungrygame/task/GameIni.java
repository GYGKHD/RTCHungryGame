package gygk.hungrygame.task;

import gygk.hungrygame.pojo.GameItem;
import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import gygk.hungrygame.util.MathUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameIni extends BukkitRunnable {

    private Player player;
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    private String uri;

    public GameIni(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> brewingLocation = gameMap.getBrewingLocation();
        for (int i = 0; i < brewingLocation.size(); i++) {
            String[] s = brewingLocation.get(i).split(",");
            Location location = new Location(player.getWorld(),
                    Double.parseDouble(s[0]),
                    Double.parseDouble(s[1]),
                    Double.parseDouble(s[2]));
            iniBrewing(location,player);
        }



    }

    private void iniBrewing(Location location, Player player) {
        World world = player.getWorld();
        Block block = world.getBlockAt(location);
        BlockData blockData = Bukkit.createBlockData(Material.BREWING_STAND);
        block.setBlockData(blockData);
        BrewingStand state = (BrewingStand) block.getState();
        state.getInventory().clear();

    }

}
