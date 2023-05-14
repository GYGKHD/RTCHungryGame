package gygk.hungrygame.task;

import gygk.hungrygame.pojo.GameMap;
import gygk.hungrygame.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.bukkit.scoreboard.*;

import java.util.Map;

public class ScoreboardTask extends BukkitRunnable {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    private String uri;

    @Override
    public void run() {
        uri = String.valueOf(config.getDataFolder()) + "\\GameMap.json";
        GameMap gameMap = FileUtil.readGameMapFiles(uri);
        List<String> playPlayer = gameMap.getPlayPlayer();
        int i = gameMap.getTime();
            for (Player player: Bukkit.getOnlinePlayers()) {
                int kill = 0;
                Scoreboard scoreboard = player.getScoreboard();
                if (scoreboard.equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
                    //如果玩家没有计分板、或者是再用公共计分板 则新建
                    scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                }
                Objective objective = scoreboard.getObjective("side-bar");
                if(Objects.isNull(objective)){
                    objective= scoreboard.registerNewObjective("side-bar", "dummy", "游戏信息");
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                }
                for (int j = 0; j < playPlayer.size(); j++) {
                    String[] split = playPlayer.get(j).split("/");
                    if(player.getName().equals(split[0])){
                        kill = Integer.parseInt(split[1]);
                    }
               }
                objective.getScore(ChatColor.GOLD + "死斗倒计时 ").setScore(5);
                objective.getScore(ChatColor.GREEN + "剩余玩家 ").setScore(4);
                objective.getScore(ChatColor.YELLOW + "击杀数 ").setScore(3);
                Map<String, String> infoMap = new HashMap<>();
                infoMap.put(ChatColor.GOLD + "死斗倒计时 ",i + "s");
                infoMap.put(ChatColor.GREEN + "剩余玩家 ",gameMap.getLivingPlayer().size() + "人");
                infoMap.put(ChatColor.YELLOW + "击杀数 ", String.valueOf(kill));

                registerOrUpdateInfoTeam(scoreboard,infoMap,player);



                Objective objective1 = scoreboard.getObjective("name-below");
                if(Objects.isNull(objective1)){
                    objective1 = scoreboard.registerNewObjective("name-below", Criteria.HEALTH, ChatColor.GREEN + "血量");
                    objective1.setDisplaySlot(DisplaySlot.BELOW_NAME);
                    objective1.setRenderType(RenderType.HEARTS);
                }
                Objective objective2 = scoreboard.getObjective("tab-list");
                if(Objects.isNull(objective2)){
                    objective2 = scoreboard.registerNewObjective("tab-list", Criteria.HEALTH, ChatColor.GREEN + "血量");
                    objective2.setDisplaySlot(DisplaySlot.PLAYER_LIST);
                    objective2.setRenderType(RenderType.HEARTS);
                }
                player.setScoreboard(scoreboard);
            }
        i--;
        gameMap.setTime(i);
        FileUtil.writeGameMapFiles(uri,gameMap);

        if(i == 0){
            cancel();
        }

    }
    public static void registerOrUpdateInfoTeam(Scoreboard scoreboard, Map<String, String> infoMap, Player player) {
        if (infoMap.size() > 0) {

            infoMap.forEach((key, val) -> {
                Team infoTeam = scoreboard.getTeam(key + "-info-team");         //name: <entry>-info-team
                if (Objects.isNull(infoTeam)) {
                    //不存在 相应的 <entry>-info-team
                    infoTeam = scoreboard.registerNewTeam(key + "-info-team");      //创建相应的infoTeam
                    infoTeam.addEntry(key);                                               //添加需要动态绑定的Entry条目
                    infoTeam.setSuffix(val);                                              //将值作为后缀，并动态影响给其他计分项
                    return;
                } else {
                    //存在 相应的 <entry>-info-team
                    if (!infoTeam.hasEntry(key)) {
                        //不存在 对应entry
                        infoTeam.addEntry(key);
                        infoTeam.setSuffix(val);
                        return;
                    } else {
                        //存在 对应entry
                        infoTeam.setSuffix(val);
                        return;
                    }
                }
            });
        }
    }
}
