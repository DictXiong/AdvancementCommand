import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Monitor implements Listener {
    Map<String, List<String>> rules;
    Monitor(FileConfiguration _config)
    {
        rules = new HashMap<String, List<String>>();
        List<Map<?,?>> tmp = _config.getMapList("rules");
        for (Map<?, ?> map : tmp) {
            Map<String, List<String>> tmp1 = (Map) map;
            for (Map.Entry<String, List<String>> j : tmp1.entrySet()) {
                rules.put(j.getKey(), j.getValue());
                Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f> rule: " + j.getKey() + " ----> " + ChatColor.RESET + j.getValue().toString());
            }
        }
        Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>§e Found " + rules.size() + " rules.");
    }
    @EventHandler
    public void onPlayerAdvancementDoneEvent(PlayerAdvancementDoneEvent e) {
        // get commands
        List<String> commands = rules.get(String.valueOf(e.getAdvancement().getKey()));
        if (commands == null) return;
        // vars
        String uuid = e.getPlayer().getUniqueId().toString();
        String player_name = e.getPlayer().getName();
        String display_name = e.getPlayer().getDisplayName();
        String advancement_id = e.getAdvancement().getKey().toString();
        // log
        Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>" + ChatColor.RESET + " Player " + e.getPlayer().getDisplayName()
                + " has reached advancement " + e.getAdvancement().getKey() + ", and " + commands.size()
                + " commands then execute.");
        // execute
        for (String s:commands)
        {
            String command = s.replaceAll("<adc:uuid>", uuid).replaceAll("<adc:player_name>", player_name).replaceAll("<adc:display_name>", display_name).replaceAll("<adc:advancement_id>", advancement_id);
            Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>" + ChatColor.RESET + " issued server command: " + command);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }
}