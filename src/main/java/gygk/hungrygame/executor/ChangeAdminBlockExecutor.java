package gygk.hungrygame.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ChangeAdminBlockExecutor implements CommandExecutor {
    Plugin config = gygk.hungrygame.HungryGame.getPlugin(gygk.hungrygame.HungryGame.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("adminblock")){
            if (sender instanceof Player){
                FileConfiguration config = this.config.getConfig();
                boolean adminblock = config.getBoolean("adminblock");
                Player player = (Player) sender;
                player.sendMessage(String.valueOf(adminblock));
                config.set("adminblock",!adminblock);

            }
        }

        return false;
    }
}
