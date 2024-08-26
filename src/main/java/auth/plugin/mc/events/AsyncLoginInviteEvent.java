package auth.plugin.mc.events;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncLoginInviteEvent extends CustomEvent {

    private final Player player;

    private final String loginUrl;

    public AsyncLoginInviteEvent(Player player, String loginUrl){
        super(true);
        this.player = player;
        this.loginUrl = loginUrl;
    }

}
