package auth.plugin.mc.events;

import auth.plugin.mc.model.Account;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncLoginEvent extends CustomEvent {

    private final Player player;

    public AsyncLoginEvent(Player player){
        super(true);
        this.player = player;
    }

}
