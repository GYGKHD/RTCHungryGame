package gygk.hungrygame.task;

import gygk.hungrygame.pojo.GameItem;
import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import gygk.hungrygame.util.MathUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class GodChest extends BukkitRunnable {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    private String uri;
    private Player player;

    public GodChest(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> chestLocation = gameMap.getChestLocation();
        int[] ints = MathUtil.randomCommon(0, chestLocation.size(), 1);
        String[] s = chestLocation.get(ints[0]).split(",");
        Location location = new Location(player.getWorld(),
                Double.parseDouble(s[0]),
                Double.parseDouble(s[1]),
                Double.parseDouble(s[2]));
        ArmorStand entity = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,20*200,1));
        createChest(location,player);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.GOLD +"坐标["+ chestLocation.get(ints[0]) + "]出现了高级宝箱");
            p.playSound(p.getLocation(),Sound.BLOCK_CHEST_OPEN,1f,1f);
        }
    }


    private void createChest(Location location, Player player) {
        World world = player.getWorld();
        Block block = world.getBlockAt(location);
        BlockData blockData = Bukkit.createBlockData(Material.CHEST);
        block.setBlockData(blockData);
//        player.sendMessage(String.valueOf(block.getType()));
        Chest chest = (Chest) block.getState();
        Inventory inventory = chest.getInventory();
        inventory.clear();
        String urp = String.valueOf(config.getDataFolder()) + "\\ManaSItem.json";
        List<GameItem> gameItems = FileUtil.readGameItemFiles(urp);
//        player.sendMessage(String.valueOf(gameItems.size()));
        Random random = new Random();
        int[] ints = MathUtil.randomCommon(5, 8, 1);
        int[] ints1 = MathUtil.randomCommon(0, gameItems.size(), ints[0]);
        int[] chestLocation = MathUtil.randomCommon(0, 26, ints1.length);
        for (int i = 0; i < ints1.length; i++) {
            ItemStack itemStack = new ItemStack(Material.getMaterial(gameItems.get(ints1[i]).getItem()),
                    gameItems.get(ints1[i]).getAmount());
            if (gameItems.get(ints1[i]).getEnchantment() != null){
                ItemMeta itemMeta = itemStack.getItemMeta();
                for (int j = 0; j < gameItems.get(ints1[i]).getEnchantment().size(); j++) {
                    String[] split = gameItems.get(ints1[i]).getEnchantment().get(j).split("/");
                    itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(split[0])),
                            Integer.parseInt(split[1]),
                            true);

                }
                itemStack.setItemMeta(itemMeta);
            }
            if(gameItems.get(ints1[i]).getName() != null){
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(gameItems.get(ints1[i]).getName());
                itemStack.setItemMeta(itemMeta);
            }
            inventory.setItem(chestLocation[i],itemStack);
        }
    }
}
