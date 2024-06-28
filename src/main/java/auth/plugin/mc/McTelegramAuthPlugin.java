package auth.plugin.mc;

import auth.plugin.mc.chat.Messages;
import auth.plugin.mc.chat.Title;
import auth.plugin.mc.commands.executors.LoginCommand;
import auth.plugin.mc.listeners.LoginMainListener;
import auth.plugin.mc.managers.LoginManager;
import auth.plugin.mc.settings.Settings;
import io.javalin.Javalin;
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
        setupHttpClient();
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

            String property = message.getKey();
            if (property.startsWith("Messages.main-title")) {
                String title = "", subtitle = "";
                int fadeIn = 0, stay = 0, fadeOut = 0;

                if (messagesConfig.isSet(property + ".title") && messagesConfig.isSet(property + ".subtitle")) {
                    title = messagesConfig.getString(property + ".title");
                    subtitle = messagesConfig.getString(property + ".subtitle");
                    fadeIn = messagesConfig.getInt(property + ".fadeIn");
                    stay = messagesConfig.getInt(property + ".stay");
                    fadeOut = messagesConfig.getInt(property + ".fadeOut");
                } else {
                    getServer().getConsoleSender().sendMessage("§cFailed to load Title " + property);
                }

                Messages.define(message, new Title(title, subtitle, fadeIn, stay, fadeOut));
            } else {
                String path = message.getKey();
                Object obj = messagesConfig.get(path);
                Messages.define(message, obj);
            }
        }
    }

    public void setupHttpClient(){
        // Temporarily switch the plugin classloader to load Javalin.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Thread.currentThread().setContextClassLoader(this.getClassLoader());
        // Create a Javalin instance.
        Javalin app = Javalin.create().start(8080);
        // Restore default loader.
        Thread.currentThread().setContextClassLoader(classLoader);
        // The created instance can be used outside the class loader.
        app.get("/", ctx -> ctx.result("Hello World!"));
        // log
        getLogger().info("JavalinPlugin is enabled");
    }

    @Override
    public void onDisable() {
    }

}
