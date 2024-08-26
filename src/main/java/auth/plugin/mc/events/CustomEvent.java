package auth.plugin.mc.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

@NoArgsConstructor
public abstract class CustomEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    public CustomEvent (boolean async) { super(async); }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public boolean callEvt(Plugin plugin) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            Bukkit.getServer().getPluginManager().callEvent(this);
        });
        return !(this instanceof Cancellable) || !((Cancellable) this).isCancelled();
    }
}
