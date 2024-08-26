package auth.plugin.mc.settings;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@RequiredArgsConstructor
public enum Settings {

    LANGUAGE("language",
            "en"
    ),
    CLIENT_PORT("client_port",
            8080),

    SERVER_IP("server_ip",
            "127.0.0.1"),

    SERVER_PORT("server_port",
            8080);

    public static final HashMap<String, Object> SETTINGS = new HashMap<>();

    @Getter
    private final String key;
    private final Object value;

    public static void define(@NonNull Settings setting, Object value) {
        SETTINGS.put(setting.key, value);
    }

    public static void clear() {
        SETTINGS.clear();
    }

    public String asString(@NonNull String def) {
        Object obj = SETTINGS.get(key);
        return (String) (!(obj instanceof String) ? def : obj);
    }

    public Integer asInt(@NonNull Integer def) {
        Object obj = SETTINGS.get(key);
        return (Integer) (!(obj instanceof Integer) ? def : obj);
    }
}
