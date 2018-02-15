package net.awesomepowered.mobarrows;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobArrows extends JavaPlugin {

    public static MobArrows instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new EventShootBow(), this);
        saveDefaultConfig();
    }
}
