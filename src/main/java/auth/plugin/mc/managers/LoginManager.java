package auth.plugin.mc.managers;

import auth.plugin.mc.util.trie.Trie;
import org.jetbrains.annotations.NotNull;

public class LoginManager {

    private final Trie logged = new Trie();

    public void cleanup(@NotNull String name) {
        synchronized (logged) {
            logged.remove(name);
        }
    }

    public void setAuthenticated(@NotNull String name) {
        synchronized (logged) {
            logged.add(name);
        }
    }

    public boolean isAuthenticated(@NotNull String name) {
        synchronized (logged) {
            return logged.find(name);
        }
    }
}
