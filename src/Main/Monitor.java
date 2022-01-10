import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
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
        for (int i = 0; i < tmp.size(); ++i)
        {
            Map<String, List<String>> tmp1 = (Map) tmp.get(i);
            for (Map.Entry<String, List<String>> j: tmp1.entrySet())
            {
                rules.put(j.getKey(), j.getValue());
                Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>§e rule: " + j.getKey() + " ----> " + j.getValue().toString());
            }
        }
        Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>§e Found " + rules.size() + " rules.");
    }
    @EventHandler
    public void onPlayerAdvancementDoneEvent(PlayerAdvancementDoneEvent e) {

        List<String> commands = rules.get(String.valueOf(e.getAdvancement().getKey()));
        Bukkit.getConsoleSender().sendMessage(String.valueOf(e.getAdvancement().getKey()));
        if (commands == null) return;
        Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>§e Player " + e.getPlayer().getDisplayName()
                + " has reached advancement " + e.getAdvancement().getKey() + ", and " + commands.size()
                + " commands then execute.");
        for (String s:commands)
        {
            Bukkit.getConsoleSender().sendMessage("§f<§aAdvancementCommand§f>§e call: " + s);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
        }

    }
}