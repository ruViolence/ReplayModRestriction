package ru.violence.replaymodrestriction;

import org.bukkit.plugin.java.JavaPlugin;
import ru.violence.replaymodrestriction.listener.PlayerJoinListener;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }
}
