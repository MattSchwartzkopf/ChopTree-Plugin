package choptree.choptree;

import choptree.choptree.command.CommandKit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ChopTree extends JavaPlugin{

    @Override
    public void onEnable() {
        getCommand("chop").setExecutor(new CommandKit());
        Bukkit.getPluginManager().registerEvents(new CommandKit(), this);
        Bukkit.broadcastMessage("Chop Tree enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.broadcastMessage("Chop Tree disabled!");
    }
}
