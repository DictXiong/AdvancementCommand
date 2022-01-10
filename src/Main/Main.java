import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {
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
        // Bukkit.getPluginCommand("demo").setExecutor(new Executor());
        //注册监听器
        Bukkit.getPluginManager().registerEvents(new Monitor(config), this);
        Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>§e enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>§e disabled.");
    }

}