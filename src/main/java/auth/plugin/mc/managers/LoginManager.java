package auth.plugin.mc.managers;

import lombok.NonNull;

import java.util.HashSet;

public class LoginManager {

    private final HashSet<String> logged = new HashSet<>();

    public void cleanup(@NonNull String name) {
        synchronized (logged) {
            logged.remove(name);
        }
    }

    public void setAuthenticated(@NonNull String name) {
        synchronized (logged) {
            logged.add(name);
        }
    }

    public boolean isAuthenticated(@NonNull String name) {
        synchronized (logged) {
            return logged.contains(name);
        }
    }

}
