package gygk.hungrygame.executor;

import gygk.hungrygame.pojo.GameItem;
import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.task.*;
import gygk.hungrygame.util.FileUtil;
import gygk.hungrygame.util.MathUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.codehaus.plexus.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManaMap implements CommandExecutor {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    private String uri;
    private List<BukkitRunnable> bukkitRunnables;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("manamap")){
            uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if(args[0].equals("create")){

                        GameMap gameMap = new GameMap();
                        gameMap.setName("HungryGame");
                        FileUtil.writeGameMapFiles(uri,gameMap);
                    }
                    if(args[0].equals("query")){
                        queryPlayer(player);
                    }
                    if (args[0].equals("sl")){
                        if (args[1].equals("add")){
                            playerSetLocation(player);
                        }
                        if (args[1].equals("query")){
                            playerQueryLocation(player);

                        }

                    }


                    if (args[0].equals("sb")){
                        if (args[1].equals("add")){
                            Location location = player.getLocation();
                            GameMap gameMap = FileUtil.readGameMapFiles(uri);
                            List<String> locations = gameMap.getBrewingLocation();
                            String locationTo = (int)location.getX() + "," + (int)location.getY() + "," + (int)location.getZ();
                            locations.add(locationTo);
                            gameMap.setBrewingLocation(locations);
                            FileUtil.writeGameMapFiles(uri,gameMap);
                            player.sendMessage(ChatColor.GOLD + "坐标 " +locationTo + " 添加成功");
                        }

                    }

                    if (args[0].equals("sc")){
                        if (args[1].equals("add")){
                            playerSetChestLocation(player);

                        }
                        if (args[1].equals("query")){
                            playerQueryChestLocation(player);
                        }
                        if (args[1].equals("remove")){
                            GameMap gameMap = FileUtil.readGameMapFiles(uri);
                            List<String> chestLocation = gameMap.getChestLocation();
                            Location location = player.getLocation();
                            String locationString = (int)location.getX() + ","  + (int)location.getY() + "," + (int)location.getZ();
                            for (int i = 0; i < chestLocation.size(); i++) {
                                if(chestLocation.get(i).equals(locationString)){
                                    chestLocation.remove(i);
                                    player.sendMessage(ChatColor.GOLD + locationString + "删除成功");
                                    gameMap.setChestLocation(chestLocation);
                                    FileUtil.writeGameMapFiles(uri,gameMap);
                                    return false;
                                }
                            }
                            player.sendMessage(ChatColor.GOLD + "不存在箱子");

                        }

                    }
                    if(args[0].equals("test")){
                        new GameIni(player).runTask(config);
                    }

                    if(args[0].equals("p")){
                        GameMap gameMap = FileUtil.readGameMapFiles(uri);
                        List<String> gameMapPlayer = new ArrayList<>();
                        for (Player p:Bukkit.getOnlinePlayers()){
                            String playerS = p.getName()+"/0";
                            gameMapPlayer.add(playerS);
                        }


                        gameMap.setPlayer(gameMapPlayer);
                        FileUtil.writeGameMapFiles(uri,gameMap);
                        player.sendMessage(ChatColor.GOLD + "添加全部玩家成功");
                    }

                    if(args[0].equals("clear")){
                        ClearScoreboardTask clearScoreboardTask = new ClearScoreboardTask();
                        clearScoreboardTask.runTask(config);
                    }
                    if (args[0].equals("start")){

                        GameMap gameMap = FileUtil.readGameMapFiles(uri);

                        if(gameMap.getPlayer().size() == 0){
                            player.sendMessage("没人啊！");
                            return false;
                        }
                        player.getServer().broadcastMessage("游戏开始");
                        gameMap.setStatus(1);

                        for (Player player1: Bukkit.getOnlinePlayers()) {
                            player1.setGameMode(GameMode.SPECTATOR);
                        }
                        gameMap.setTime(600);
                        FileUtil.writeGameMapFiles(uri,gameMap);

                        List<String> chestLocation = gameMap.getChestLocation();

                        for (int i = 0; i < chestLocation.size(); i++) {
                            String[] split = chestLocation.get(i).split(",");
                            Location location = new Location(player.getWorld(),
                                    Double.parseDouble(split[0]),
                                    Double.parseDouble(split[1]),
                                    Double.parseDouble(split[2]));
                            createChest(location,player);
                        }

                        List<Entity> entities = player.getWorld().getEntities();
                        for (int i = 0; i < entities.size(); i++) {
                            if (String.valueOf(entities.get(i).getType()) == "ARROW" || String.valueOf(entities.get(i).getType()) == "DROPPED_ITEM") {
                                entities.get(i).remove();

                            }
                        }

                        List<Entity> entitiess = player.getWorld().getEntities();
                        for (Entity e: entitiess) {
                            if(e.getType() == EntityType.ARMOR_STAND){
                                e.remove();
                            }
                        }

                        List<String> players = gameMap.getPlayer();
                        List<String> location = gameMap.getLocation();

                        int[] ints = MathUtil.randomCommon(0, location.size(), players.size());
                        for (int i = 0; i < players.size(); i++) {
                            String[] splitL = location.get(ints[i]).split(",");
                            String[] split = players.get(i).split("/");
                            Player iPlayer = Bukkit.getPlayer(split[0]);
                            Location ll = new Location(player.getWorld(),
                                    Double.parseDouble(splitL[0]),
                                    Double.parseDouble(splitL[1]),
                                    Double.parseDouble(splitL[2]));

                            iPlayer.teleport(ll);
                            iPlayer.setGameMode(GameMode.SURVIVAL);
                            iPlayer.setHealth(20);
                            iPlayer.setFoodLevel(20);
                            iPlayer.setSaturation(20);
                            iPlayer.getInventory().clear();
                            iPlayer.setExp(0);
                            iPlayer.setLevel(0);
                            iPlayer.sendTitle("游戏开始","努力活下去吧!");
                            iPlayer.playSound(player.getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1f,1f);

                        }

                        player.getServer().dispatchCommand(player,"effect clear @e");

                        gameMap.setPlayPlayer(players);
                        List<String> livingPlayer = new ArrayList<>();
                        for (int i = 0; i < players.size(); i++) {
                            String[] split = players.get(i).split("/");
                            livingPlayer.add(split[0]);
                        }

                        gameMap.setLivingPlayer(livingPlayer);
                        FileUtil.writeGameMapFiles(uri,gameMap);
    //                    new GameStart(player).runTaskAsynchronously(config);

                        new GameIni(player).runTask(config);

                        BukkitTask bukkitTaskGodChest = new GodChest(player).runTaskLater(config,20 * 200);
                        BukkitTask bukkitTaskGodChest1 = new GodChest(player).runTaskLater(config,20 * 400);

                        ScoreboardTask scoreboardTask = new ScoreboardTask();
                        BukkitTask bukkitTaskScoreboardTask = scoreboardTask.runTaskTimer(config, 0, 20);

                        CheckPlayer checkPlayer = new CheckPlayer(uri, player.getServer());
                        checkPlayer.runTaskTimerAsynchronously(config,0,10);

                        DuelTask duelTask = new DuelTask(player.getServer(), player.getWorld(),player);
                        BukkitTask bukkitTaskDuelTask = duelTask.runTaskLaterAsynchronously(config, 20 * 600);

                        DuelTpTask duelTpTask = new DuelTpTask(player.getServer(), player.getWorld());
                        BukkitTask bukkitTaskDuelTpTask = duelTpTask.runTaskTimer(config, 0, 10);

                        List<BukkitTask> bukkitTasks = new ArrayList<>();
                        bukkitTasks.add(bukkitTaskDuelTask);
                        bukkitTasks.add(bukkitTaskDuelTpTask);
                        bukkitTasks.add(bukkitTaskScoreboardTask);
                        bukkitTasks.add(bukkitTaskGodChest);
                        bukkitTasks.add(bukkitTaskGodChest1);

                        GameEndTask gameEndTask = new GameEndTask(bukkitTasks);
                        gameEndTask.runTaskTimer(config,0,10);
                    }


                }
        }
        return false;
    }



    private void createChest(Location location,Player player) {
        World world = player.getWorld();
        Block block = world.getBlockAt(location);
        BlockData blockData = Bukkit.createBlockData(Material.CHEST);
        block.setBlockData(blockData);
//        player.sendMessage(String.valueOf(block.getType()));
        Chest chest = (Chest) block.getState();
        Inventory inventory = chest.getInventory();
        inventory.clear();
        String uri = String.valueOf(config.getDataFolder()) + "\\ManaItem.json";
        List<GameItem> gameItems = FileUtil.readGameItemFiles(uri);
        Random random = new Random();
        int[] ints = MathUtil.randomCommon(6, 9, 1);
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

    private void playerQueryChestLocation(Player player) {
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> chestLocations = gameMap.getChestLocation();
        player.sendMessage("一共" + chestLocations.size() + "个箱子");
        for (int i = 0; i < chestLocations.size(); i++) {
            player.sendMessage("[" + chestLocations.get(i) + "]");
        }
    }

    private void playerSetChestLocation(Player player) {
        Location location = player.getLocation();
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> chestLocations = gameMap.getChestLocation();
        String locationTo = (int)location.getX() + "," + (int)location.getY() + "," + (int)location.getZ();
        chestLocations.add(locationTo);
        gameMap.setChestLocation(chestLocations);
        FileUtil.writeGameMapFiles(uri,gameMap);
        player.sendMessage(ChatColor.GOLD + "坐标 " +locationTo + " 添加成功");
    }

    private void playerQueryLocation(Player player) {
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> locations = gameMap.getLocation();
        player.sendMessage("一共" + locations.size() + "个出生点");
        for (int i = 0; i < locations.size(); i++) {
            player.sendMessage("[" + locations.get(i) + "]");
        }
    }

    private void playerSetLocation(Player player) {
        Location location = player.getLocation();
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> locations = gameMap.getLocation();
        String locationTo = (int)location.getX() + "," + (int)location.getY() + "," + (int)location.getZ();
        locations.add(locationTo);
        gameMap.setLocation(locations);
        FileUtil.writeGameMapFiles(uri,gameMap);
        player.sendMessage(ChatColor.GOLD + "坐标 " +locationTo + " 添加成功");
    }

    private void queryPlayer(Player player) {
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> players = gameMap.getPlayer();
        for (int i = 0; i < players.size(); i++) {
            String[] split = players.get(i).split("/");
            player.sendMessage(split[0]);
        }
        player.sendMessage("共有"+players.size()+"人");
    }
}
