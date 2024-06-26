package auth.plugin.mc;

import auth.plugin.mc.chat.Messages;
import auth.plugin.mc.commands.executors.LoginCommand;
import auth.plugin.mc.listeners.LoginMainListener;
import auth.plugin.mc.managers.LoginManager;
import auth.plugin.mc.settings.Settings;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class McTelegramAuthPlugin extends JavaPlugin {

    private LoginManager loginManager;

    @Override
    public void onEnable() {
        loginManager = new LoginManager();
        setupSettings();
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

    private void setupSettings(){
        File configFile = new File(getDataFolder(), "config.yml");
        configFile.getParentFile().mkdir();
        saveResource("config.yml", false);
        if (!configFile.exists()){
            getServer().getConsoleSender().sendMessage("§cFailed to create 'config.yml' file in " + getDataFolder());
        }

        Settings.clear();
        for (Settings setting : Settings.values()){
            Settings.define(setting, getConfig().get(setting.getKey()));
        }

        String language = Settings.LANGUAGE.asString("en");
        File messageFile = new File(getDataFolder() + File.separator + "lang", "language_" + language + ".yml");
        saveResource("lang" + File.separator + "language_" + language + ".yml", false);
        if (!messageFile.exists()){
            getServer().getConsoleSender().sendMessage("§cFailed to create messages file.");
        }

        YamlConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messageFile);
        for (Messages message : Messages.values()){
            String path = message.getKey();
            Object obj = messagesConfig.get(path);
            Messages.define(message, obj);
        }
    }

    @Override
    public void onDisable() {
    }

}
