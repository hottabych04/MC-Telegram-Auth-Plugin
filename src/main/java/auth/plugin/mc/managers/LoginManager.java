package auth.plugin.mc.managers;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class LoginManager {

    private final HashSet<String> logged = new HashSet<>();

    public boolean cleanup(@NotNull String name) {
        synchronized (logged) {
            return logged.remove(name);
        }
    }

    public void setAuthenticated(@NotNull String name) {
        synchronized (logged) {
            logged.add(name);
        }
    }

    public boolean isAuthenticated(@NotNull String name) {
        synchronized (logged) {
            return logged.contains(name);
        }
    }
}
