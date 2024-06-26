package auth.plugin.mc.settings;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@RequiredArgsConstructor
public enum Settings {

    LANGUAGE("language",
            "en"
    );

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
}
