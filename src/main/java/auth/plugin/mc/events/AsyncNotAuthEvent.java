package auth.plugin.mc.events;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncNotAuthEvent extends CustomEvent{

    private final Player player;
    private final String errMsg;

    public AsyncNotAuthEvent(Player player, String errMsg){
        super(true);
        this.player = player;
        this.errMsg = errMsg;
    }

}
