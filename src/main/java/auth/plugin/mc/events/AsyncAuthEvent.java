package auth.plugin.mc.events;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncAuthEvent extends CustomEvent{

    private final Player player;

    public AsyncAuthEvent(Player player){
        super(true);
        this.player = player;
    }
}
