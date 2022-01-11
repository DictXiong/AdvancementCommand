import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin implements CommandExecutor {
    @Override
    public void onLoad() {
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>§e loaded.");
    }

    @Override
    public void onEnable() {
        FileConfiguration config = getConfig();
        if (!config.getBoolean("enable"))
        {
            Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>§e is disabled in config.");
            return;
        }

        //绑定指令与执行器
        //注：此处绑定的指令必须要先在plugin.yml中注册
        Bukkit.getPluginCommand("adc").setExecutor(this);
        //注册监听器
        Bukkit.getPluginManager().registerEvents(new Monitor(config), this);
        Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>§e enabled.");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>§e disabled.");
    }

    public void reload() {
        onDisable();
        reloadConfig();
        onEnable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (sender.hasPermission("advancementcommand")) {
            if (args != null && args.length >= 1 && Objects.equals(args[0], "reload")) {
                sender.sendMessage("§f<§aAdvancementCommand§f>§e reloading...");
                reload();
            } else if (args != null && args.length >= 1 && Objects.equals(args[0], "trigger")) {
                if (args.length < 3)
                {
                    sender.sendMessage("Usage: /adc trigger <player> <advancement>");
                }
                else
                {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke " + args[1] + " only " + args[2]);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + args[1] + " only " + args[2]);
                }
            } else {
                sender.sendMessage("Usage:\n  /adc reload - reload rules\n  /adc trigger <player> <advancement> - revoke and grant advancement to player");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You are not permitted to use this command.");
        }
        return true;
    }

}