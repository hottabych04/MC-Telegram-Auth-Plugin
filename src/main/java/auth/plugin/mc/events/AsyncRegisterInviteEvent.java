package auth.plugin.mc.events;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncRegisterInviteEvent extends CustomEvent {

    private final Player player;

    private final String registrationUrl;

    public AsyncRegisterInviteEvent(Player player, String registerUrl){
        super(true);
        this.player = player;
        this.registrationUrl = registerUrl;
    }

}
