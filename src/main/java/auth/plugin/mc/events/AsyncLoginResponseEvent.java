package auth.plugin.mc.events;

import auth.plugin.mc.model.Account;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncLoginResponseEvent extends CustomEvent {

    private final Player player;

    private final Account account;

    public AsyncLoginResponseEvent(Player player){
        super(true);
        this.player = player;
        this.account = Account.buildByPlayer(player);
    }

}
