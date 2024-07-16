package auth.plugin.mc.events;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncNotAuthEvent extends CustomEvent{

    private final Player player;

    public AsyncNotAuthEvent(Player player){
        super(true);
        this.player = player;
    }

}
