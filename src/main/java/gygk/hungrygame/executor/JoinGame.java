package gygk.hungrygame.executor;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class JoinGame implements CommandExecutor {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("game")){
            String uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(args[0].equals("join")){

                    GameMap gameMap = FileUtil.readGameMapFiles(uri);
                    List<String> gameMapPlayer = gameMap.getPlayer();
                    for (int i = 0; i < gameMapPlayer.size(); i++) {
                        String[] split = gameMapPlayer.get(i).split("/");
                        if(split[0].equals(player.getName())){
                            player.sendMessage(ChatColor.GOLD +"你已经在队列里面了");
                            return false;
                        }
                    }
                    String playerS = player.getName()+"/0";
                    gameMapPlayer.add(playerS);
                    gameMap.setPlayer(gameMapPlayer);
                    FileUtil.writeGameMapFiles(uri,gameMap);
                    player.sendMessage(ChatColor.GOLD + "成功加入游戏,现在有"+ gameMapPlayer.size() + "人在队列中");
                }
                if(args[0].equals("leave")){
                    GameMap gameMap = FileUtil.readGameMapFiles(uri);
                    List<String> gameMapPlayer = gameMap.getPlayer();
                    for (int i = 0; i < gameMapPlayer.size(); i++) {
                        String[] split = gameMapPlayer.get(i).split("/");
                        if(split[0].equals(player.getName())){
                            player.sendMessage(ChatColor.GOLD +"你已经退出队列了");
                            gameMapPlayer.remove(i);
                            gameMap.setPlayer(gameMapPlayer);
                            FileUtil.writeGameMapFiles(uri,gameMap);
                            return false;
                        }
                    }
                    player.sendMessage(ChatColor.GOLD +"你似乎没有加入过游戏");
                }
                if(args[0].equals("list")){
                    GameMap gameMap = FileUtil.readGameMapFiles(uri);
                    List<String> gameMapPlayer = gameMap.getPlayer();
                    player.sendMessage(gameMapPlayer.size() +  "人");

                }


            }
        }
        return false;
    }
}
