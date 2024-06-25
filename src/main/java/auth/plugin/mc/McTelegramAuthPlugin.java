package auth.plugin.mc;

import auth.plugin.mc.commands.executors.LoginCommand;
import auth.plugin.mc.listeners.LoginMainListener;
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
    }

    private void setupListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new LoginMainListener(this), this);
    }

    private void setupCommands(){
        getCommand("login").setExecutor(new LoginCommand(this));
    }

    @Override
    public void onDisable() {
    }

}
