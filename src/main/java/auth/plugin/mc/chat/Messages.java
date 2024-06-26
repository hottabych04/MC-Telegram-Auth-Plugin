package auth.plugin.mc.chat;

import auth.plugin.mc.settings.Settings;
import lombok.Getter;
import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;

public enum Messages {

    BEFORE_REGISTER("main-message.before.register"),
    BEFORE_LOGIN("main-message.before.login"),
    AFTER_REGISTER("main-message.after.register"),
    AFTER_LOGIN("main-message.after.login");

    @Getter
    private final String key;

    Messages(String key){ this.key = "Messages." + key; }

    public static void define(@NonNull Messages message, Object value) {
        if (value instanceof String) {
            value = ChatColor.translateAlternateColorCodes('&', (String) value);
        }

        Settings.SETTINGS.put(message.key, value);
    }

    public String asString() {
        return asString("§cMissing message: " + key);
    }

    public String asString(@NonNull String def) {
        Object obj = Settings.SETTINGS.get(key);
        return (String) (!(obj instanceof String) ? def : obj);
    }

}
