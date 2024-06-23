package auth.plugin.mc;

import auth.plugin.mc.commands.executors.LoginExecutor;
import auth.plugin.mc.listeners.PlayerMainListener;
import auth.plugin.mc.managers.LoginManager;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class McTelegramAuthPlugin extends JavaPlugin {

    private LoginManager loginManager;

    @Override
    public void onEnable() {
        loginManager = new LoginManager();
        setupListeners();
        setupCommands();
        getServer().getConsoleSender().sendMessage("[" + getName() + "] Test first message on enable" );
    }

    private void setupListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerMainListener(this), this);
    }

    private void setupCommands(){
        getCommand("login").setExecutor(new LoginExecutor(this));
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("[" + getName() + "] Test first message on disable" );
    }

}
