package auth.plugin.mc.events;

import auth.plugin.mc.model.Account;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncRegisterResponseEvent extends CustomEvent {

    private final Player player;

    private final String registrationUrl;

    public AsyncRegisterResponseEvent(Player player, String registerUrl){
        super(true);
        this.player = player;
        this.registrationUrl = registerUrl;
    }

}
