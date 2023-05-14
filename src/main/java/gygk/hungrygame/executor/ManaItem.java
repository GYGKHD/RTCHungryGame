package gygk.hungrygame.executor;

import gygk.hungrygame.pojo.GameItem;
import gygk.hungrygame.util.FileUtil;
import gygk.hungrygame.util.MathUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ManaItem implements CommandExecutor {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("manaitem")){

            if(sender instanceof Player) {
                Player player = (Player) sender;
                if (args[0].equals("add")) {
                    addItem(player);
                }
                if (args[0].equals("get") && args[1] != null) {
                    getItem(player, Integer.parseInt(args[1]));

                }
                if (args[0].equals("remove") && args[1] != null) {
                    removeItem(player, Integer.parseInt(args[1]));

                }
                if (args[0].equals("create")) {
                    createChest(player);

                }

                if (args[0].equals("sadd")) {
                    String urp = String.valueOf(config.getDataFolder()) + "\\ManaSItem.json";
                    PlayerInventory inventory = player.getInventory();
                    ItemStack itemInMainHand = inventory.getItemInMainHand();
                    GameItem gameItem = new GameItem();
                    List<GameItem> gameItems = FileUtil.readGameItemFiles(urp);
                    gameItem.setItem(String.valueOf(itemInMainHand.getType()));
                    gameItem.setAmount(itemInMainHand.getAmount());
                    gameItem.setName(itemInMainHand.getItemMeta().getDisplayName());
                    Map<Enchantment, Integer> enchantments = itemInMainHand.getEnchantments();
                    List<String> enchantmentString = new ArrayList<>();
                    for (Enchantment key : enchantments.keySet()) {
                        String enchantment = key.getKey().getKey() + "/" +enchantments.get(key);
                        enchantmentString.add(enchantment);
                    }
                    gameItem.setEnchantment(enchantmentString);
                    gameItem.setLore(itemInMainHand.getItemMeta().getLore());


                    player.sendMessage(ChatColor.GOLD +"添加成功");
                    gameItems.add(gameItem);
                    FileUtil.writeGameItemFiles(urp, gameItems);
                }
                if (args[0].equals("sget") && args[1] != null) {
                    String urip = String.valueOf(config.getDataFolder()) + "\\ManaSItem.json";
                    List<GameItem> gameItems = FileUtil.readGameItemFiles(urip);
                    int size = 54;
                    int l = Integer.parseInt(args[1]);
                    Inventory inventory = Bukkit.createInventory(player, size, "游戏物品");
                    for (int i = size * (l - 1); i < size * l; i++) {
                        if(gameItems.size() > i){
                            ItemStack itemStack = new ItemStack(Material.getMaterial(gameItems.get(i).getItem()),
                                    gameItems.get(i).getAmount());
                            if (gameItems.get(i).getEnchantment() != null){
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                for (int j = 0; j < gameItems.get(i).getEnchantment().size(); j++) {
                                    String[] split = gameItems.get(i).getEnchantment().get(j).split("/");
                                    itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(split[0])),
                                            Integer.parseInt(split[1]),
                                            false);

                                }
                                itemStack.setItemMeta(itemMeta);
                            }
                            if(gameItems.get(i).getName() != null){
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.setDisplayName(gameItems.get(i).getName());
                                itemStack.setItemMeta(itemMeta);
                            }
                            int i1 = inventory.firstEmpty();
                            inventory.setItem(i1,itemStack);

                        }

                    }
                    player.openInventory(inventory);

                }
                if (args[0].equals("sremove") && args[1] != null) {
                    removeItem(player, Integer.parseInt(args[1]));

                }

                
                
            }
        }
        return false;
    }

    private void createChest(Player player) {
        Location location = player.getLocation();
        World world = player.getWorld();
        Block block = world.getBlockAt(location);
        BlockData blockData = Bukkit.createBlockData(Material.CHEST);
        block.setBlockData(blockData);
//        player.sendMessage(String.valueOf(block.getType()));
        Chest chest = (Chest) block.getState();
        Inventory inventory = chest.getInventory();
        String uri = String.valueOf(config.getDataFolder()) + "\\ManaItem.json";
        List<GameItem> gameItems = FileUtil.readGameItemFiles(uri);
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
                            false);

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

    private void removeItem(Player player, int parseInt) {
        String uri = String.valueOf(config.getDataFolder()) + "\\ManaItem.json";
        List<GameItem> gameItems = FileUtil.readGameItemFiles(uri);
        gameItems.remove(parseInt - 1);
        FileUtil.writeGameItemFiles(uri, gameItems);
        player.sendMessage("删除成功！");
    }

    private void getItem(Player player,int l) {
        String uri = String.valueOf(config.getDataFolder()) + "\\ManaItem.json";
        List<GameItem> gameItems = FileUtil.readGameItemFiles(uri);
        int size = 54;

        Inventory inventory = Bukkit.createInventory(player, size, "游戏物品");
        for (int i = size * (l - 1); i < size * l; i++) {
            if(gameItems.size() > i){
                ItemStack itemStack = new ItemStack(Material.getMaterial(gameItems.get(i).getItem()),
                        gameItems.get(i).getAmount());
                if (gameItems.get(i).getEnchantment() != null){
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    for (int j = 0; j < gameItems.get(i).getEnchantment().size(); j++) {
                        String[] split = gameItems.get(i).getEnchantment().get(j).split("/");
                        itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(split[0])),
                                Integer.parseInt(split[1]),
                                true);

                    }
                    itemStack.setItemMeta(itemMeta);
                }
                if(gameItems.get(i).getName() != null){
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(gameItems.get(i).getName());
                    itemStack.setItemMeta(itemMeta);
                }
                int i1 = inventory.firstEmpty();
                inventory.setItem(i1,itemStack);

            }

            }
        player.openInventory(inventory);

    }

    private void addItem(Player player) {
        String uri = String.valueOf(config.getDataFolder()) + "\\ManaItem.json";
        PlayerInventory inventory = player.getInventory();
        ItemStack itemInMainHand = inventory.getItemInMainHand();
        GameItem gameItem = new GameItem();
        List<GameItem> gameItems = FileUtil.readGameItemFiles(uri);
        gameItem.setItem(String.valueOf(itemInMainHand.getType()));
        gameItem.setAmount(itemInMainHand.getAmount());
        gameItem.setName(itemInMainHand.getItemMeta().getDisplayName());
        Map<Enchantment, Integer> enchantments = itemInMainHand.getEnchantments();
        List<String> enchantmentString = new ArrayList<>();
        for (Enchantment key : enchantments.keySet()) {
            String enchantment = key.getKey().getKey() + "/" +enchantments.get(key);
            enchantmentString.add(enchantment);
        }
        gameItem.setEnchantment(enchantmentString);
        gameItem.setLore(itemInMainHand.getItemMeta().getLore());


        player.sendMessage(ChatColor.GOLD +"添加成功");
        gameItems.add(gameItem);
        FileUtil.writeGameItemFiles(uri, gameItems);
    }
}
